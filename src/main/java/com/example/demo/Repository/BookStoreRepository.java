package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Book;

public interface BookStoreRepository extends JpaRepository<Book,Integer>{
	 @Query(value = "select * from book where book_name like :%bookName%", nativeQuery = true)
	    List<Book> findByBookName(String bookName);


	    @Query(value = "select * from book order by price", nativeQuery = true)
	    List<Book> getSortedListOfBooksInAsc();

	    @Query(value = "select * from book order by price desc", nativeQuery = true)
	    List<Book> getSortedListOfBooksInDesc();

}
