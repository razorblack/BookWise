package me.razorblack.bookwise.repository;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.model.Patron;

import java.util.*;

@Slf4j
public class InMemoryPatronRepository implements PatronRepository {
    // Patron Storage:  ID -> Patron
    private final Map<String, Patron> store = new HashMap<>();

    @Override
    public void save(Patron patron) {
        store.put(patron.getId(), patron);
        log.info("Saved patron: {}", patron.getId());
    }

    @Override
    public Optional<Patron> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }


    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
        log.info("Deleted patron: {}", id);
    }
}