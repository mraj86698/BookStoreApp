package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Book;
import com.example.demo.Model.Cart;
import com.example.demo.Model.UserRegistration;
import com.example.demo.Repository.BookStoreCartRepository;
import com.example.demo.Repository.BookStoreRepository;
import com.example.demo.Repository.UserRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {

	@Autowired
	BookStoreRepository bookStoreRepository;
	@Autowired
	UserRegistrationRepository userRegistrationRepository;
	@Autowired
	BookStoreCartRepository bookStoreCartRepository;

	/**
	 * Add Cart to Book Details
	 */
	@Override
	public Cart insertItems(CartDTO cartdto) {
		Optional<Book> book = bookStoreRepository.findById(cartdto.getBookId());
		Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(cartdto.getUserId());
		if (book.isPresent() && userRegistration.isPresent()) {
			Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get());
			bookStoreCartRepository.save(newCart);
			return newCart;
		} else {
			throw new BookStoreException("Book or User does not exists");
		}
	}
	/**
	 * To get All Cart Details
	 */
	@Override
	public ResponseDTO getCartDetails() {
		List<Cart> getCartDetails = bookStoreCartRepository.findAll();
		ResponseDTO dto = new ResponseDTO();
		if (getCartDetails.isEmpty()) {
			String message = " Not found Any Cart details ";
			dto.setMessage(message);
			dto.setData(0);
			return dto;

		} else {
			dto.setMessage("the list of cart items is sucussfully retrived");
			dto.setData(getCartDetails);
			return dto;
		}
	}
	/**
	 * To Get Particular Cart Details
	 */
	@Override
	public Optional<Cart> getCartDetailsById(Integer cartId) {
		Optional<Cart> getCartData = bookStoreCartRepository.findById(cartId);
		if (getCartData.isPresent()) {
			return getCartData;
		} else {
			throw new BookStoreException(" Didn't find any record for this particular cartId");
		}
	}
	/**
	 * To Delete Cart Details
	 */
	@Override
	public Optional<Cart> deleteCartItemById(Integer cartId) {
		Optional<Cart> deleteData = bookStoreCartRepository.findById(cartId);
		if (deleteData.isPresent()) {
			bookStoreCartRepository.deleteById(cartId);
			return deleteData;
		} else {
			throw new BookStoreException(" Did not get any cart for specific cart id ");
		}

	}
	/**
	 * To Update Cart Details
	 */


	@Override
	public Cart updateRecordById(Integer cartId, CartDTO cartDTO) {
		Optional<Cart> cart = bookStoreCartRepository.findById(cartId);
		Optional<Book> book = bookStoreRepository.findById(cartDTO.getBookId());
		Optional<UserRegistration> user = userRegistrationRepository.findById(cartDTO.getUserId());
		if (cart.isEmpty()) {
			throw new BookStoreException("Cart Record doesn't exists");
		} else {
			if (book.isPresent() && user.isPresent()) {
				Cart newCart = new Cart(cartId, cartDTO.getQuantity(), book.get(), user.get());
				bookStoreCartRepository.save(newCart);
				log.info("Cart record updated successfully for id " + cartId);
				return newCart;
			} else {
				throw new BookStoreException("Book or User doesn't exists");
			}
		}
	}

	@Override
	public Cart updateQuantity(Integer id, Integer quantity) {
		Optional<Cart> cart = bookStoreCartRepository.findById(id);
		Optional<Book> book = bookStoreRepository.findById(cart.get().getBook().getBookId());
		if (cart.isEmpty()) {
			throw new BookStoreException("Cart Record doesn't exists");
		} else {
			if (quantity < book.get().getQuantity()) {
				cart.get().setQuantity(quantity);
				bookStoreCartRepository.save(cart.get());
				log.info("Quantity in cart record updated successfully");
				book.get().setQuantity(book.get().getQuantity() - (quantity - cart.get().getQuantity()));
				bookStoreRepository.save(book.get());
				return cart.get();
			} else {
				throw new BookStoreException("Requested quantity is not available");
			}
		}
	}




}
