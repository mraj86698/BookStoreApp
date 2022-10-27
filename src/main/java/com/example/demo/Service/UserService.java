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
        UserRegistration userRegistration= new UserRegistration(userDTO);
        userRepository.save(userRegistration);
        String token = util.createToken(userRegistration.getUserId());
        mailService.sendEmail(userRegistration.getEmail(), "Test Email", "Registered SuccessFully, hii: "
                +userRegistration.getFirstName()+"Please Click here to get data-> "
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
            mailService.sendEmail("mraj8669865@gmail.com", "Test Email", "Get your data with this token, hii: "
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
     Optional<UserRegistration> login = userRepository.findByEmailid(email);
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
    public String forgotPassword(String email, String password) {
        Optional<UserRegistration> isUserPresent = userRepository.findByEmailid(email);

        if(!isUserPresent.isPresent()) {
            throw new BookStoreException("Book record does not found");
        }
        else {
            UserRegistration user = isUserPresent.get();
            user.setPassword(password);
            userRepository.save(user);
            return "Password updated successfully";
        }

    }

    @Override
    public Object getUserByEmailId(String emailId) {

        return userRepository.findByEmailid(emailId);
    }

	@Override
    public UserRegistration updateRecordByToken(Integer id, UserDTO userDTO) {

        Optional<UserRegistration> addressBook = userRepository.findById(id);
        if(addressBook.isEmpty()) {
            throw new BookStoreException("User Details for id not found");
        }
        UserRegistration newBook = new UserRegistration(id,userDTO);
        userRepository.save(newBook);

        return newBook;
    }










}