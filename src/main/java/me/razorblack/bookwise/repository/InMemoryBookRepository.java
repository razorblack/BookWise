package me.razorblack.bookwise.repository;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.model.Book;

import java.util.*;

@Slf4j
public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> store = new HashMap<>();

    @Override
    public void save(Book book) {
        store.put(book.getIsbn(), book);
        log.info("Saved book: {}", book.getIsbn());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(store.get(isbn));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteByIsbn(String isbn) {
        store.remove(isbn);
        log.info("Deleted book: {}", isbn);
    }
}