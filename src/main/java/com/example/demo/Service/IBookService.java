package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;

public interface IBookService {
	 Book createBook(BookDTO bookDTO);

	    Optional<Book> getBookDataById(int BookId);

	    List<Book> getAllBookData();

	    Book updateRecordById(Integer BookId, BookDTO bookDTO);

	    Object deleteRecordByToken(int BookId);

	    List<Book> sortedListOfBooksInAscendingOrder();

	    List<Book> sortedListOfBooksInDescendingOrder();

	    Book updateQuantity(Integer id, Integer quantity);

}
