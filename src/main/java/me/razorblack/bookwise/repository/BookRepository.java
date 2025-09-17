package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.exception.ConfigurationNotFoundException;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.util.ConfigFileReader;

import java.util.List;
import java.util.Optional;

import static me.razorblack.bookwise.constants.BaseConstants.APP_DATA_STORAGE;
import static me.razorblack.bookwise.constants.BaseConstants.IN_MEMORY;

/**
 * BookRepository interface for managing book data storage.
 */
public interface BookRepository {
    /**
     * Saves a book to the repository.
     * @param book the book to be saved
     */
    void save(Book book);

    /**
     * Finds a book by its ID.
     * @param id the ID of the book to be found
     * @return an Optional containing the found book, or empty if not found
     */
    Optional<Book> findById(String id);

    /**
     * Finds all books in the repository.
     * @return a list of all books
     */
    List<Book> findAll();

    /**
     * Deletes a book by its ID.
     * @param bookId the ID of the book to be deleted
     */
    void delete(String bookId);

    /**
     * Factory method to get the appropriate BookRepository implementation based on configuration.
     * @return an instance of BookRepository
     */
    static BookRepository getInstance() {
        if (ConfigFileReader.CONFIG.getPropertyValue(APP_DATA_STORAGE).equals(IN_MEMORY)) {
            return new InMemoryBookRepository();
        } else {
            throw new ConfigurationNotFoundException("Data storage configuration is not provided correctly.");
        }
    }
}
