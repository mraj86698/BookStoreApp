package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.Order;

public interface IOrderService {

	 public Order addOrder(String token,OrderDTO orderdto);

	 public String cancelOrder(int orderId, String token);

	    public List<Order> getAllOrder();

	    public Order getOrderById(Integer orderId);

	   // public Order updateOrder(Integer orderId, OrderDTO dto);

	    public Order deleteOrder(Integer orderId);





		//public List<Order> userOrders(int userId);

		public List<Order> getUserOrders(String token);

}
