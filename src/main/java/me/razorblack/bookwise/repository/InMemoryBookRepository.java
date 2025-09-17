package me.razorblack.bookwise.repository;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.model.Book;

import java.util.*;

/**
 * In-memory implementation of the BookRepository interface.
 */
@Slf4j
public class InMemoryBookRepository implements BookRepository {
    // Book storage <Book Id, Book>
    private final Map<String, Book> store = new HashMap<>();

    @Override
    public void save(Book book) {
        store.put(book.getBookId(), book);
        log.info("Saved book: {}", book.getBookId());
    }

    @Override
    public Optional<Book> findById(String bookId) {
        return Optional.ofNullable(store.get(bookId));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
        log.warn("Removed book: {}", id);
    }
}