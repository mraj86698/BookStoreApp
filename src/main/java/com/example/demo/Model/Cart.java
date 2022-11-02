package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartId;
    @OneToOne
    @JoinColumn(name = "userId")
    private UserRegistration user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private Integer quantity;
    private Integer totalPrice;

    public Cart(Integer cartId, int quantity, Book book, UserRegistration user,int totalPrice) {
        super();
        this.cartId = cartId;
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        this.totalPrice=totalPrice;
    }

    public Cart(int quantity, Book book, UserRegistration user,int totalPrice) {
        super();
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        this.totalPrice=totalPrice;
    }


}
