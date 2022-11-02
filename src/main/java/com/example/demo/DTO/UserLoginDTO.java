package com.example.demo.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserLoginDTO {
   @Email
    private String email;
   @NotEmpty(message = "Password can't be null")
    private String password;
   public UserLoginDTO(String email,String password){
       this.email=email;
       this.password=password;
   }

}
