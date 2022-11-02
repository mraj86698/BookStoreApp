package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.UserRegistration;

public interface IUserService {

	  String addUser(UserDTO userDTO);

	    List<UserRegistration> getAllUsers();

	    String loginUser(String email_id, String password);

	    Object getUserById(String token);

	    String forgotPassword(String email, String password);

	    Object getUserByEmailId(String emailId);

		UserRegistration updateRecordByToken(Integer id, UserDTO userDTO);





}
