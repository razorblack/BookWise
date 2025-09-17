package me.razorblack.bookwise.service;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.PatronNotFoundException;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.repository.PatronRepository;

import java.util.List;

/**
 * Service class for managing patrons.
 */
@Slf4j
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    /**
     * Add a new patron to the system.
     * @param name name of the patron
     * @param contact contact information of the patron
     */
    public void addPatron(String name, String contact) {
        Patron newPatron = new Patron(name, contact);
        patronRepository.save(newPatron);
        log.info("Added patron: {} - {}", newPatron.getId(), newPatron.getName());
    }

    /**
     * Update an existing patron's information.
     * @param id ID of the patron to update
     * @param name new name of the patron
     * @param contact new contact information of the patron
     */
    public void updatePatron(String id, String name, String contact) {
        Patron p = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException("Patron not found: " + id));
        p.setName(name);
        p.setContact(contact);
        patronRepository.save(p);
        log.info("Updated patron: {}", id);
    }

    /**
     * Get all patrons in the system.
     * @return List of all patrons.
     */
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
}
