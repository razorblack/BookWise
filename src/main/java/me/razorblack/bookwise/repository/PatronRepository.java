package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.exception.ConfigurationNotFoundException;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.util.ConfigFileReader;

import java.util.List;
import java.util.Optional;

import static me.razorblack.bookwise.constants.BaseConstants.APP_DATA_STORAGE;
import static me.razorblack.bookwise.constants.BaseConstants.IN_MEMORY;

/**
 * Repository interface for managing Patron entities.
 */
public interface PatronRepository {

    /**
     * Factory method to get the appropriate PatronRepository implementation based on configuration.
     * @return An instance of PatronRepository.
     */
    static PatronRepository getInstance() {
        if (ConfigFileReader.CONFIG.getPropertyValue(APP_DATA_STORAGE).equals(IN_MEMORY)) {
            return new InMemoryPatronRepository();
        } else {
            throw new ConfigurationNotFoundException("Data storage configuration is not provided correctly.");
        }
    }

    /**
     * Saves a Patron entity.
     * @param patron The Patron entity to be saved.
     */
    void save(Patron patron);

    /**
     * Finds a Patron entity by its ID.
     * @param id The ID of the Patron entity.
     * @return
     */
    Optional<Patron> findById(String id);

    /**
     * Finds all Patron entities.
     * @return A list of all Patron entities.
     */
    List<Patron> findAll();

    /**
     * Deletes a Patron entity by its ID.
     * @param id The ID of the Patron entity to be deleted.
     */
    void delete(String id);
}