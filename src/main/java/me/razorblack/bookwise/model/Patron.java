package me.razorblack.bookwise.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Patron class representing a library patron.
 */
@Getter
@Setter
public class Patron {
    private static final AtomicInteger patronCounter = new AtomicInteger(1);
    private final String id;
    private String name;
    private String contact;
    // Borrowing history loan ids
    private final List<String> borrowingHistory = new ArrayList<>();

    public Patron(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        patronCounter.incrementAndGet();
    }

    public Patron(String name, String contact) {
        this.id = "P" + patronCounter.incrementAndGet();
        this.name = name;
    }
}
