package me.razorblack.bookwise.repository;

import me.razorblack.bookwise.model.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    void save(Patron patron);
    Optional<Patron> findById(String id);
    List<Patron> findAll();
    void deleteById(String id);
}