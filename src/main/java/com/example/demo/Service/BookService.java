package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Book;
import com.example.demo.Repository.BookStoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService implements IBookService{

    @Autowired
    BookStoreRepository bookStoreRepository;
    /**
     * Add New Book Data
     */
    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO);
          return  bookStoreRepository.save(newBook);

    }
    /**
     * Get Particular Book Data By BookId
     */
    @Override
    public Optional<Book> getBookDataById(int BookId) {
        Optional<Book> getBook=bookStoreRepository.findById(BookId);
        if(getBook.isEmpty()){
            throw new BookStoreException("Book Store Details for id not found");
        }
        return getBook;

    }
    /**
     * Get All Book Data
     */

    @Override
    public List<Book> getAllBookData() {
        List<Book> getBooks=bookStoreRepository.findAll();
        return getBooks;
    }
    /**
     * Update Book data by Book Id
     */
    @Override
    public Book updateRecordById(Integer BookId, BookDTO bookDTO) {

        Optional<Book> updateBook = bookStoreRepository.findById(BookId);
        if (updateBook.isEmpty()) {
            throw new BookStoreException("Book record does not found");
        } else {
            Book updateUser = new Book(BookId, bookDTO);
            bookStoreRepository.save(updateUser);
            return updateUser;

        }
    }

    /**
     * Delete Book data By BookId
     */
    @Override
    public String deleteRecordByToken(int BookId) {
        Optional<Book> newBook = bookStoreRepository.findById(BookId);
        if (newBook.isEmpty()) {
            throw new BookStoreException("Book record does not found");
        } else {
           bookStoreRepository.deleteById(BookId);
        }
        return "data deleted succesfull";
    }


    /**
     * Sorted Ascending order by Book Price
     */
    @Override
    public List<Book> sortedListOfBooksInAscendingOrder() {
        List<Book> getSortedList=  bookStoreRepository.getSortedListOfBooksInAsc();
        return getSortedList;
    }
    /**
     * Sorted descending order by Book Price
     */
    @Override
    public List<Book> sortedListOfBooksInDescendingOrder() {
        List<Book> getSortedListInDesc=  bookStoreRepository.getSortedListOfBooksInDesc();
        return getSortedListInDesc;
    }
    /**
     * update the Book Quantity
     */
    @Override
    public Book updateQuantity(Integer id, Integer quantity) {
        Optional<Book> book = bookStoreRepository.findById(id);
        if(book.isEmpty()) {
            throw new BookStoreException("Book Record doesn't exists");
        }
        else {
            book.get().setQuantity(quantity);
            bookStoreRepository.save(book.get());
            log.info("Quantity for book record updated successfully for id "+id);
            return book.get();
        }
    }


}