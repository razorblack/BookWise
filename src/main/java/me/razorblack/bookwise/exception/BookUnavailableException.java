package me.razorblack.bookwise.exception;

/**
 * Exception thrown when a book is unavailable for loan.
 */
public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String msg){ super(msg); }
}
