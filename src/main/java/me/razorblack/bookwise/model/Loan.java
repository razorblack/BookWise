package me.razorblack.bookwise.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Loan {
    @NonNull
    private final String loanId; // e.g., UUID
    @NonNull
    private final String isbn;
    @NonNull
    private final String patronId;
    @NonNull
    private final LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
