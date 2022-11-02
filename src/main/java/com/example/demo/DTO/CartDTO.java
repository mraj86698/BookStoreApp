package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CartDTO {
    private Integer userId;
    private Integer bookId;
    @NotNull(message="Book quantity yet to be provided")
    private Integer quantity;
}
