package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.exception.ConfigurationNotFoundException;
import me.razorblack.bookwise.model.Loan;
import me.razorblack.bookwise.util.ConfigFileReader;

import static me.razorblack.bookwise.constants.BaseConstants.APP_DATA_STORAGE;
import static me.razorblack.bookwise.constants.BaseConstants.IN_MEMORY;

public interface LoanRepository {
    static LoanRepository getInstance() {
        if (ConfigFileReader.CONFIG.getPropertyValue(APP_DATA_STORAGE).equals(IN_MEMORY)) {
            return new InMemoryLoanRepository();
        } else {
            throw new ConfigurationNotFoundException("Data storage configuration is not provided correctly.");
        }
    }

    void save(Loan loan);

    Loan findById(String loanId);
}
