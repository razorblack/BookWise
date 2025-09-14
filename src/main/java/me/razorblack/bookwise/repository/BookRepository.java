package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    void deleteByIsbn(String isbn);
}
