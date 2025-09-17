package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.exception.ConfigurationNotFoundException;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.util.ConfigFileReader;

import java.util.List;
import java.util.Optional;

import static me.razorblack.bookwise.constants.BaseConstants.APP_DATA_STORAGE;
import static me.razorblack.bookwise.constants.BaseConstants.IN_MEMORY;

public interface PatronRepository {
    static PatronRepository getInstance() {
        if (ConfigFileReader.CONFIG.getPropertyValue(APP_DATA_STORAGE).equals(IN_MEMORY)) {
            return new InMemoryPatronRepository();
        } else {
            throw new ConfigurationNotFoundException("Data storage configuration is not provided correctly.");
        }
    }
    void save(Patron patron);
    Optional<Patron> findById(String id);
    List<Patron> findAll();
    void delete(String id);
}