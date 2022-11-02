package com.example.demo.Service;

import java.util.List;
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



    List<Cart> getCartDetailsByUser(String token);

	/**
	 * To update Quantity
	 */
	Cart updateQuantity(String token, Integer cartId, int quantity);





}
