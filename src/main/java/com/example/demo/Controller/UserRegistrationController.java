package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Model.UserRegistration;
import com.example.demo.Service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserRegistrationController {

    @Autowired
    IUserService userRegistrationService;

    /**
     * Register User Details
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserInBookStore( @Valid @RequestBody UserDTO userDTO){
        String newUser= userRegistrationService.addUser(userDTO);
        ResponseDTO responseDTO=new ResponseDTO("User Registered Successfully",newUser);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
  /**
   * Get Mapping Using to Get All User Details
   * @return
   */
    @GetMapping(value = "/getAll")
    public ResponseEntity<String> getAllUser()
    {
        List<UserRegistration> listOfUsers = userRegistrationService.getAllUsers();
        ResponseDTO dto = new ResponseDTO("User retrieved successfully (:",listOfUsers);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    /**
     * Update the Existing User Details
     * @param id
     * @param userDTO
     * @return
     */
    @PutMapping("/update/{token}")
    public ResponseEntity<String> updateUserById(@PathVariable String token,@Valid @RequestBody UserDTO userDTO){
        UserRegistration entity = userRegistrationService.updateUser(token,userDTO);
        ResponseDTO dto = new ResponseDTO("User Data Updated Successfully",entity);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    /**
     * Login in to The User
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public String userLogin(@RequestParam String email,@RequestParam String password) {
        UserLoginDTO userLoginDTO=new UserLoginDTO(email, password);
        String response = userRegistrationService.loginUser(userLoginDTO.getEmail(),userLoginDTO.getPassword());
        return response;
    }

    /**
     * Get Particular User Details  by Token
     * @param token
     * @return
     */
    @GetMapping("/getUserBy/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data By Id",
                userRegistrationService.getUserById(token)), HttpStatus.OK);
    }


     /**
      * Forgot password using User EmailId
      * @param email
      * @param password
      * @return
      */

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, @RequestParam String password,@RequestParam String newpassword) {
        String resp = userRegistrationService.forgotPassword(email,password,newpassword);
        return new ResponseEntity(resp, HttpStatus.OK);
    }







}