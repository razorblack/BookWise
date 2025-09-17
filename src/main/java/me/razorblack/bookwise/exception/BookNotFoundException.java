package me.razorblack.bookwise.exception;

/**
 * Custom exception to be thrown when a book is not found in the library system.
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String msg){ super(msg); }
}
