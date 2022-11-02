package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.UserRegistration;
import com.example.demo.Repository.UserRegistrationRepository;
import com.example.demo.Util.EmailSenderService;
import com.example.demo.Util.TokenUtility;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRegistrationRepository userRepository;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility util;
    /**
     * Add User Details
     */
    @Override
    public String addUser(UserDTO userDTO) {
        UserRegistration user= new UserRegistration(userDTO);
        userRepository.save(user);
        String token = util.createToken(user.getUserId());
        mailService.sendEmail(user.getEmail(), "Registered SuccessFully", "Thank you for Registering Book Store App,hii: "
                +user.getFirstName()+"Please Click here to get data-> "
                +"http://localhost:8081/user/getBy/"+token);
        return token;
    }
    /**
     * To get User Details
     */
    @Override
    public List<UserRegistration> getAllUsers() {
        List<UserRegistration> getUsers= userRepository.findAll();
        return getUsers;
    }

    /**
     * Get Particular User By Id Using Token
     */
    @Override
    public Object getUserById(String token) {
        int id=util.decodeToken(token);
        Optional<UserRegistration> getUser=userRepository.findById(id);
        if(getUser.isEmpty()){
            throw new BookStoreException("Record for provided userId is not found");
        }
        else {
            mailService.sendEmail("mraj8669865@gmail.com", "This is Your Token For Decode Your data", "Get your data with this token, hii: "
                    +getUser.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8080/user/getBy/"+token);
            return getUser;
        }

    }
   /**
    * Get Login for User
    */
    @Override
    public String loginUser(String email, String password) {
     Optional<UserRegistration> login = userRepository.findByEmail(email);
     if(login.isPresent()){
         String pass = login.get().getPassword();
         if(login.get().getPassword().equals(password)){
             return "User Login successfully";
         }

         else {
             return "Wrong Password";
         }
     }
        return "User not found";
    }


    /**
     * forgot password for User
     */

    @Override
    public String forgotPassword(String email, String oldpassword, String newPassword) {
        Optional<UserRegistration> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String password = user.get().getPassword();
            if (password.equals(oldpassword)) {
                UserRegistration user1 = user.get();
                user1.setPassword(newPassword);
                userRepository.save(user1);
                mailService.sendEmail(user1.getEmail(), "Password Reset mail", "User password reset successfully");
                return "Password updated successfully";
            }else throw new BookStoreException("Please enter correct password");
        }
        return "User not found";
    }

    @Override
    public UserRegistration getByToken(String token) {
        int id = util.decodeToken(token);
        return userRepository.findById(id).orElseThrow(() -> new BookStoreException("User data not found"));
    }
	@Override
    public UserRegistration updateUser(String token, UserDTO userDTO) {

        UserRegistration user=this.getByToken(token);
        user.updateUser(userDTO);
        mailService.sendEmail(user.getEmail(), "Update mail", "User data updated successfully");
        return userRepository.save(user);

    }










}