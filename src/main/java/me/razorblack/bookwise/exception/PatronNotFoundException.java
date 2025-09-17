package me.razorblack.bookwise.exception;

/**
 * Exception thrown when a patron is not found in the system.
 */
public class PatronNotFoundException extends RuntimeException {
    public PatronNotFoundException(String msg){ super(msg); }
}
