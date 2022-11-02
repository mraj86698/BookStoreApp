package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Order;


public interface OrderRepository extends JpaRepository<Order,Integer> {


	  @Query(value = "select * from order_details where user_id =:userId", nativeQuery = true)
	    List<Order> findAllByUserId(int userId);

//	    @Query(value = "select * from order_details where cancel =:cancel", nativeQuery = true)
//	    List<Order> getAllOrders(boolean cancel);
}