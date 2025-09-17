package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.model.Loan;

import java.util.HashMap;
import java.util.Map;

public class InMemoryLoanRepository implements LoanRepository {
    // Loan Repo: loanId -> Loan
    private final Map<String, Loan> loans = new HashMap<>();

    @Override
    public void save(Loan loan) {
        loans.put(loan.getLoanId(), loan);
    }

    @Override
    public Loan findById(String loanId) {
        try {
            return loans.get(loanId);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }
}
