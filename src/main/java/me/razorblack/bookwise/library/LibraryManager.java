package me.razorblack.bookwise.library;

import lombok.Getter;
import me.razorblack.bookwise.repository.BookRepository;
import me.razorblack.bookwise.repository.LoanRepository;
import me.razorblack.bookwise.repository.PatronRepository;
import me.razorblack.bookwise.service.BookService;
import me.razorblack.bookwise.service.LoanService;
import me.razorblack.bookwise.service.PatronService;

public class LibraryManager {
    private static LibraryManager instance;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;

    @Getter
    private final BookService bookService;
    @Getter
    private final PatronService patronService;
    @Getter
    private final LoanService loanService;

    private LibraryManager() {
        this.bookRepository = BookRepository.getInstance();
        this.patronRepository = PatronRepository.getInstance();
        this.loanRepository = LoanRepository.getInstance();
        this.bookService = new BookService(bookRepository);
        this.patronService = new PatronService(patronRepository);
        this.loanService = new LoanService(bookRepository, patronRepository, loanRepository);
    }

    public static LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    /**
     * Method to load initial seed data from the json file
     */
    public void bootstrapSampleData() {
        loadBooksData();
        loadPatronData();
    }

    private void loadPatronData() {
    }

    private void loadBooksData() {
        
    }
}
