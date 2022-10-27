package com.example.demo.Exception;

import lombok.Data;

@Data
public class BookStoreException extends RuntimeException{
    public BookStoreException(String message){
        super(message);
    }

}
