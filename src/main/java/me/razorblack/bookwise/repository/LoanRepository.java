package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.exception.ConfigurationNotFoundException;
import me.razorblack.bookwise.model.Loan;
import me.razorblack.bookwise.util.ConfigFileReader;

import static me.razorblack.bookwise.constants.BaseConstants.APP_DATA_STORAGE;
import static me.razorblack.bookwise.constants.BaseConstants.IN_MEMORY;

/**
 * Repository interface for managing Loan entities.
 */
public interface LoanRepository {

    /**
     * Factory method to get the appropriate LoanRepository implementation based on configuration.
     * @return An instance of LoanRepository.
     */
    static LoanRepository getInstance() {
        if (ConfigFileReader.CONFIG.getPropertyValue(APP_DATA_STORAGE).equals(IN_MEMORY)) {
            return new InMemoryLoanRepository();
        } else {
            throw new ConfigurationNotFoundException("Data storage configuration is not provided correctly.");
        }
    }

    /**
     * Saves a Loan entity.
     * @param loan The Loan entity to be saved.
     */
    void save(Loan loan);

    /**
     * Finds a Loan entity by its ID.
     * @param loanId The ID of the Loan entity.
     * @return
     */
    Loan findById(String loanId);
}
