package me.razorblack.bookwise.exception;


public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String msg){ super(msg); }
}
