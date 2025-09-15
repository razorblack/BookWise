package me.razorblack.bookwise;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.library.LibraryManager;
import me.razorblack.bookwise.model.Loan;
import me.razorblack.bookwise.util.ConfigFileReader;

import java.util.Scanner;

@Slf4j
public class App {
    static {
        init();
    }

    /**
     * Initialising Application
     */
    private static void init() {
        loadConfiguration();
        loadBookData();
        loadPatronData();
    }

    private static void loadPatronData() {
    }

    private static void loadBookData() {

    }

    private static void loadConfiguration() {
        ConfigFileReader.loadConfig();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userType = scanner.nextInt();

        LibraryManager lm = LibraryManager.getInstance();
        lm.bootstrapSampleData();

        // Simple demonstration: pick first book and first patron
        String isbn = "978-0134685991"; // Effective Java
        String patronId = lm.getPatronService().all().get(0).getId();

        log.info("Attempting checkout...");
        Loan loan = lm.getLoanService().checkout(isbn, patronId, 14);
        log.info("Loan created: {}", loan.getLoanId());

        log.info("Attempting to checkout same book again (should throw)...");
        try {
            lm.getLoanService().checkout(isbn, patronId, 14);
        } catch (Exception ex) {
            log.error("Expected error on double checkout: {}", ex.getMessage());
        }

        log.info("Returning book...");
        lm.getLoanService().returnBook(loan.getLoanId());

        log.info("Thanks for using BookWise!");
    }
}
