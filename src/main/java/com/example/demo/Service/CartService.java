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
import com.example.demo.Util.TokenUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {

	@Autowired
	BookStoreRepository bookRepo;
	@Autowired
	UserRegistrationRepository userRepo;
	@Autowired
	BookStoreCartRepository cartRepo;

	@Autowired
    TokenUtility util;

	/**
	 * Add Cart to Book Details
	 */
	@Override
	public Cart insertItems(CartDTO cartdto) {
		Optional<Book> book = bookRepo.findById(cartdto.getBookId());
		Optional<UserRegistration> userRegistration = userRepo.findById(cartdto.getUserId());
		if (book.isPresent() && userRegistration.isPresent()) {
			int totalPrice = book.get().getPrice() * cartdto.getQuantity();
			Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get(),totalPrice);
			cartRepo.save(newCart);
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
		List<Cart> getCartDetails = cartRepo.findAll();
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
		Optional<Cart> getCartData = cartRepo.findById(cartId);
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
		Optional<Cart> deleteData = cartRepo.findById(cartId);
		if (deleteData.isPresent()) {
			cartRepo.deleteById(cartId);
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
		Optional<Cart> cart = cartRepo.findById(cartId);
		Optional<Book> book = bookRepo.findById(cartDTO.getBookId());
		Optional<UserRegistration> user = userRepo.findById(cartDTO.getUserId());
		if (cart.isEmpty()) {
			throw new BookStoreException("Cart Record doesn't exists");
		} else {
			if (book.isPresent() && user.isPresent()) {
				int totalPrice = book.get().getPrice() * cartDTO.getQuantity();
				Cart newCart = new Cart(cartId, cartDTO.getQuantity(), book.get(), user.get(),totalPrice);
				cartRepo.save(newCart);
				log.info("Cart record updated successfully for id " + cartId);
				return newCart;
			} else {
				throw new BookStoreException("Book or User doesn't exists");
			}
		}
	}


	/**
	 * To update Quantity
	 */


	@Override
	public Cart updateQuantity(String token, Integer cartId, int quantity) {
		int userId = util.decodeToken(token);
		 UserRegistration user = userRepo.findById(userId).orElse(null);
		Optional<Cart> cart = cartRepo.findById(cartId);
		Optional<Book> book = bookRepo.findById(cart.get().getBook().getBookId());
		if (cart!=null&& user!=null && book !=null) {
			cart.get().setQuantity(quantity);
            cart.get().setTotalPrice(book.get().getPrice() * quantity);
            return cartRepo.save(cart.get());
		}

		else {
				throw new BookStoreException("Requested quantity is not available");
			}
		}

	/**
	 * To Get Cart Details BY User Id
	 */
	@Override
    public List<Cart> getCartDetailsByUser(String token) {
		int userId = util.decodeToken(token);
        List<Cart> userCartList = cartRepo.getCartListByUser(userId);
        if(userCartList.isEmpty()){
            return null;
        }else
            return userCartList;
    }

}
