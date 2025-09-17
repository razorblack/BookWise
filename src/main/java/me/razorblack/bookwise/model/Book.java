package me.razorblack.bookwise.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Book {
    private static final AtomicInteger bookCounter = new AtomicInteger(1);
    private final String bookId;
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean available = true;

    public Book(String bookId, String isbn, String title, String author) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        bookCounter.incrementAndGet();
    }

    public Book(String isbn, String title, String author, int publicationYear) {
        this.bookId = "BK" + bookCounter.incrementAndGet();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
