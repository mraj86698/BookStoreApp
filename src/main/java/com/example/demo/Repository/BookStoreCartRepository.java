package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Cart;

public interface BookStoreCartRepository extends JpaRepository<Cart,Integer> {
	@Query(value = "select * from cart where user_id= :userId", nativeQuery = true)
    List<Cart> getCartListByUser(int userId);


}