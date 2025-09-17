package me.razorblack.bookwise.service;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.BookNotFoundException;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public void addNewBook(String isbn, String title, String author, int year) {
        Book newBook = new Book(isbn, title, author, year);
        newBook.setAvailable(true);
        repo.save(newBook);
        log.info("Added book: {} - {}", newBook.getIsbn(), newBook.getTitle());
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public void updateBook(String id, String title, String author, int year) {
        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found: " + id));
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(year);
        repo.save(book);
        log.info("Updated book: {}", id);
    }

    public void removeBook(String bookId) {
        repo.delete(bookId);
        log.info("Removed book: {}", bookId);
    }

    public Book findBookById(String bookId) {
        return repo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
    }

    public List<Book> findBookByISNB(String isbn) {
        return repo.findAll().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    public List<Book> searchByTitle(String title) {
        return repo.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return repo.findAll().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void setAvailability(String bookId, boolean available) {
        Book book = repo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
        book.setAvailable(available);
        repo.save(book);
        log.info("Book {} availability changed to {}", bookId, available);
    }

    public List<Book> getAllAvailableBooks() {
        return repo.findAll().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBorrowedBooks() {
        return repo.findAll().stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }
}
