package com.example.demo.Service;

import java.util.Optional;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Model.Cart;

public interface ICartService {
	ResponseDTO getCartDetails();

    Optional<Cart> getCartDetailsById(Integer cartId);

    Optional<Cart> deleteCartItemById(Integer cartId);

    Cart updateRecordById(Integer cartId, CartDTO cartDTO);

    Cart insertItems(CartDTO cartdto);

    Cart updateQuantity(Integer id, Integer quantity);



}
