package me.razorblack.bookwise.service;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.BookNotFoundException;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing books in the library system.
 */
@Slf4j
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    /**
     * Adds a new book to the repository.
     * @param isbn ISBN number
     * @param title Book title
     * @param author Book author
     * @param year Publication year
     */
    public void addNewBook(String isbn, String title, String author, int year) {
        Book newBook = new Book(isbn, title, author, year);
        newBook.setAvailable(true);
        repo.save(newBook);
        log.info("Added book: {} - {}", newBook.getIsbn(), newBook.getTitle());
    }

    /**
     * Retrieves all books from the repository.
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    /**
     * Updates an existing book's details.
     * @param id Book ID
     * @param title Book title
     * @param author Book author
     * @param year Publication year
     */
    public void updateBook(String id, String title, String author, int year) {
        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found: " + id));
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(year);
        repo.save(book);
        log.info("Updated book: {}", id);
    }

    /**
     * Removes a book from the repository.
     * @param bookId Book ID
     */
    public void removeBook(String bookId) {
        repo.delete(bookId);
        log.info("Removed book: {}", bookId);
    }

    /**
     * Finds a book by its ID.
     * @param bookId Book ID
     * @return
     */
    public Book findBookById(String bookId) {
        return repo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
    }

    /**
     * Finds books by their ISBN.
     * @param isbn ISBN number
     * @return List of books with the given ISBN
     */
    public List<Book> findBookByISBN(String isbn) {
        return repo.findAll().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    /**
     * Searches for books by title (case-insensitive, partial match).
     * @param title Book title
     * @return List of books matching the title
     */
    public List<Book> searchByTitle(String title) {
        return repo.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for books by author (case-insensitive, partial match).
     * @param author Book author
     * @return List of books matching the author
     */
    public List<Book> searchByAuthor(String author) {
        return repo.findAll().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Sets the availability status of a book.
     * @param bookId Book ID
     * @param available Availability status
     */
    public void setAvailability(String bookId, boolean available) {
        Book book = repo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
        book.setAvailable(available);
        repo.save(book);
        log.info("Book {} availability changed to {}", bookId, available);
    }

    /**
     * Gets all available books.
     * @return List of available books
     */
    public List<Book> getAllAvailableBooks() {
        return repo.findAll().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Gets all borrowed books.
     * @return List of borrowed books
     */
    public List<Book> getAllBorrowedBooks() {
        return repo.findAll().stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }
}
