package me.razorblack.bookwise.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class Loan {
    private static final AtomicInteger loanCounter = new AtomicInteger(1);
    private final String loanId; // e.g., UUID
    private final String bookId;
    private final String isbn;
    private final String patronId;
    private final LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(String bookId, String isbn, String patronId, LocalDate checkoutDate) {
        this.loanId = "LOAN_" + loanCounter.getAndIncrement();
        this.bookId = bookId;
        this.isbn = isbn;
        this.patronId = patronId;
        this.checkoutDate = checkoutDate;
    }
}
