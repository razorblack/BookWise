package me.razorblack.bookwise.exception;

/**
 * Exception thrown when a book is unavailable for loan.
 */
public class ConfigurationNotFoundException extends RuntimeException{
    public ConfigurationNotFoundException(String msg){ super(msg); }
}
