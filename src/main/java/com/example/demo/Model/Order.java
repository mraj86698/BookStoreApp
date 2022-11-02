package com.example.demo.Model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orderDetails")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;
    private LocalDate orderDate;
    private int  quantity;
    private String address;

    @JoinColumn(name="userId")
    @OneToOne
    private UserRegistration user;

    @JoinColumn(name="bookId")
    @ManyToOne
    private Book book;
    private boolean cancel=false;
    private int orderPrice;

    public Order(UserRegistration user, Book book,int orderPrice, OrderDTO orderDto) {
    	this.orderDate = LocalDate.now();
        this.quantity=orderDto.getQuantity();
        this.address=orderDto.getAddress();
        this.orderPrice=orderPrice;
        this.user=user;
        this.book = book;
    }
}
