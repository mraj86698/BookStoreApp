package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.UserRegistration;

public interface IUserService {

	  String addUser(UserDTO userDTO);

	    List<UserRegistration> getAllUsers();

	    String loginUser(String email_id, String password);

	    Object getUserById(String token);

	    //String forgotPassword(String email, String password);

		UserRegistration updateUser(String token, UserDTO userDTO);

		UserRegistration getByToken(String token);

		/**
		 * forgot password for User
		 */
		String forgotPassword(String email, String password, String newPassword);





}
