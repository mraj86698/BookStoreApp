package com.example.demo.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDTO {
   @Email
    private String email;
   @NotEmpty(message = "Password can't be null")
    private String password;


}
