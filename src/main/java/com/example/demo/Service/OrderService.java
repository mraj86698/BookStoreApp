package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Book;
import com.example.demo.Model.Order;
import com.example.demo.Model.UserRegistration;
import com.example.demo.Repository.BookStoreCartRepository;
import com.example.demo.Repository.BookStoreRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRegistrationRepository;
import com.example.demo.Util.EmailSenderService;
import com.example.demo.Util.TokenUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private BookStoreRepository bookRepo;
    @Autowired
    private UserRegistrationRepository userRepo;
    @Autowired
    BookStoreCartRepository cartRepo;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    private IUserService userService;
    @Autowired
    TokenUtility util;

    /**
     * Add Order Details
     */
    @Override
	public Order addOrder(String token,OrderDTO orderdto) {
        Book book = bookRepo.findById(orderdto.getBookId()).orElse(null);
        UserRegistration user = userService.getByToken(token);
        System.out.println("calling");
        if (user!=null) {
        	int orderPrice = book.getPrice() * orderdto.getQuantity();
        	//Remove Quantity of Books after Order Placed
            book.setQuantity(book.getQuantity()- orderdto.getQuantity());
                Order order = new Order(user,book,orderPrice,orderdto);
                orderRepo.save(order);
                cartRepo.deleteAll();
                log.info("Order record inserted successfully");
                mailService.sendEmail(user.getEmail(),
                        "Your Order was Successfully Placed",
                        "Order Placed with Given Details \n"
                                +"Book Name :"+order.getBook().getBookName()+"\n"
                                +"Book Description :"+order.getBook().getBookDescription()+"\n"
                                +"Book Price :"+order.getBook().getPrice()+"\n"
                                +"Book AuthorName:"+order.getBook().getAuthorName()+"\n"
                                +"Order Quantity :"+orderdto.getQuantity()
                                +"\n"+"Order Price :"+orderPrice);
                return order;
        } else {
            throw new BookStoreException("Book or User doesn't exists");
        }
    }
    /**
     * Cancel Order Details
     */
    @Override
    public String cancelOrder(int orderId, String token) {
        UserRegistration user = userService.getByToken(token);
        if (user != null) {
            Order order = orderRepo.findById(orderId).orElse(null);
            Book book = bookRepo.findById(order.getBook().getBookId()).orElse(null);

            if (order != null) {
                order.setCancel(true);
                orderRepo.save(order);
                //To Change Quantity after Calcellation of Order
                book.setQuantity(book.getQuantity() + order.getQuantity());
                //Cancel Mail
                mailService.sendEmail(user.getEmail(),"Your Order Was Successfully Cancelled","Order was Cancelled\nOrder Id:"+orderId+"Details Are\n"
                		 +"Book Name :"+order.getBook().getBookName()+"\n"
                         +"Book Description :"+order.getBook().getBookDescription()+"\n"
                         +"Book Price :"+order.getBook().getPrice()+"\n"
                         +"Book AuthorName:"+order.getBook().getAuthorName()+"\n"
                         );
                return "Order Cancelled";
            }else throw new BookStoreException("Order details not found");
        }else throw new BookStoreException("User does not exists");
    }
    /**
     * To get All Order Details by userId
     */

    @Override
	public List<Order> getUserOrders(String token) {
        UserRegistration user = userService.getByToken(token);
        if (user != null) {
        	int userId = util.decodeToken(token);
            return orderRepo.findAllByUserId(userId);
        } else throw new BookStoreException("Orders for userId not found");
    }


    /**
     * To Get All Order Details
     */
    @Override
	public List<Order> getAllOrder() {
        List<Order> orderList = orderRepo.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }
    /**
     * To Get Particular Order Details
     */
    @Override
	public Order getOrderById(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            log.info("Order record retrieved successfully for id " + id);
            return order.get();
        }
    }

    /**
     * To Delete the Order Details
     */
    @Override
	public Order deleteOrder(Integer orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            orderRepo.deleteById(orderId);
            log.info("Order record deleted successfully for id " + orderId);
            return order.get();
        }
    }
}
