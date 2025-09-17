package me.razorblack.bookwise.service;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.PatronNotFoundException;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.repository.PatronRepository;

import java.util.List;

@Slf4j
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public void addPatron(String name, String contact) {
        Patron newPatron = new Patron(name, contact);
        patronRepository.save(newPatron);
        log.info("Added patron: {} - {}", newPatron.getId(), newPatron.getName());
    }

    public void updatePatron(String id, String name, String contact) {
        Patron p = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException("Patron not found: " + id));
        p.setName(name);
        p.setContact(contact);
        patronRepository.save(p);
        log.info("Updated patron: {}", id);
    }

    public Patron findById(String id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException("Patron not found: " + id));
    }

    public List<Patron> all() {
        return patronRepository.findAll();
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
}
