package me.razorblack.bookwise.service;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.BookNotFoundException;
import me.razorblack.bookwise.exception.BookUnavailableException;
import me.razorblack.bookwise.exception.PatronNotFoundException;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.model.Loan;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.repository.BookRepository;
import me.razorblack.bookwise.repository.LoanRepository;
import me.razorblack.bookwise.repository.PatronRepository;

import java.time.LocalDate;

@Slf4j
public class LoanService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;

    public LoanService(BookRepository bookRepository, PatronRepository patronRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
    }

    public Loan checkout(String bookId, String patronId, int days) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found: " + bookId));
        if (!book.isAvailable()) {
            throw new BookUnavailableException("Book is currently borrowed: " + bookId);
        }
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFoundException("Patron not found: " + patronId));

        // create loan
        LocalDate now = LocalDate.now();
        Loan loan = new Loan(book.getBookId(), bookId, patronId, now);
        loan.setDueDate(now.plusDays(days));

        loanRepository.save(loan);

        // mark book unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // add to patron history
        patron.getBorrowingHistory().add(loan.getLoanId());
        patronRepository.save(patron);

        log.info("Checked out book {} to patron {} until {}", bookId, patronId, loan.getDueDate());
        return loan;
    }

    public void returnBook(String loanId) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            log.warn("Return attempted for unknown loan {}", loanId);
            return;
        }
        if (loan.getReturnDate() != null) {
            log.warn("Loan {} already returned", loanId);
            return;
        }
        loan.setReturnDate(LocalDate.now());
        // mark book available
        Book book = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new BookNotFoundException("Book not found: " + loan.getBookId()));
        book.setAvailable(true);
        bookRepository.save(book);
        log.info("Returned book {} from loan {}", loan.getIsbn(), loanId);
    }

    public void returnBook(String patronId, String bookId) {
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFoundException("Patron not found: " + patronId));
        // find active loan for this book and patron
        for (String loanId : patron.getBorrowingHistory()) {
            Loan loan = loanRepository.findById(loanId);
            if (loan != null && loan.getBookId().equals(bookId) && loan.getReturnDate() == null) {
                returnBook(loanId);
                return;
            }
        }
        log.warn("No active loan found for patron {} and book {}", patronId, bookId);
    }
}
