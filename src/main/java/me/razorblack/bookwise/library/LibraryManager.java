package me.razorblack.bookwise.library;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.JsonNode;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.repository.BookRepository;
import me.razorblack.bookwise.repository.LoanRepository;
import me.razorblack.bookwise.repository.PatronRepository;
import me.razorblack.bookwise.service.BookService;
import me.razorblack.bookwise.service.LoanService;
import me.razorblack.bookwise.service.PatronService;
import me.razorblack.bookwise.util.JsonFileReader;
import me.razorblack.bookwise.util.Validator;

import static me.razorblack.bookwise.constants.BaseConstants.SEED_BOOKS_FILE_PATH;
import static me.razorblack.bookwise.constants.BaseConstants.SEED_PATRON_FILE_PATH;

/**
 * Singleton class to manage the library system
 */
@Slf4j
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

    /**
     * Method to get the singleton instance of LibraryManager
     * @return LibraryManager instance
     */
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

    /**
     * Method to load initial seed data for patrons from the json file
     */
    private void loadPatronData() {
        try {
            JsonNode root = JsonFileReader.readJson(SEED_PATRON_FILE_PATH);
            if (root.isArray()) {
                root.forEach(branchNode -> {
                    String branchId = textOrNull(branchNode, "branchId");
                    if (Validator.isActiveBranch(branchId)) {
                        JsonNode patronsNode = branchNode.get("patrons");
                        if (patronsNode != null && patronsNode.isArray()) {
                            patronsNode.forEach(patronNode -> {
                                try {
                                    String id = textOrNull(patronNode, "id");
                                    String name = textOrNull(patronNode, "name");
                                    String phone = textOrNull(patronNode, "phone");
                                    if (id != null && name != null) {
                                        Patron patron = new Patron(id, name, phone);
                                        patronRepository.save(patron);
                                        log.info("Seeded patron: {} - {}", patron.getName(), patron.getId());
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to seed patron entry: {}", patronNode, e);
                                }
                            });
                        }
                    }
                });
            }
            log.info("Patron seed data loaded");
        } catch (Exception e) {
            log.error("Exception occurred while loading patron seed data", e);
        }
    }

    /**
     * Method to load initial seed data for books from the json file
     */
    private void loadBooksData() {
        try {
            JsonNode root = JsonFileReader.readJson(SEED_BOOKS_FILE_PATH);
            if (root.isArray()) {
                root.forEach(branchNode -> {
                    String branchId = textOrNull(branchNode, "branchId");
                    if (Validator.isActiveBranch(branchId)) {
                        JsonNode booksNode = branchNode.get("books");
                        if (booksNode != null && booksNode.isArray()) {
                            booksNode.forEach(bookNode -> {
                                try {
                                    String id = textOrNull(bookNode, "id");
                                    String title = textOrNull(bookNode, "title");
                                    String author = textOrNull(bookNode, "author");
                                    String isbn = textOrNull(bookNode, "isbn");
                                    int publicationYear = intOrDefault(bookNode, "publicationYear", 0);
                                    if (id != null && isbn != null && title != null && author != null) {
                                        Book book = new Book(id, isbn, title, author);
                                        book.setPublicationYear(publicationYear);
                                        book.setAvailable(true);
                                        bookRepository.save(book);
                                        log.info("Seeded book: {} - {}", book.getTitle(), book.getIsbn());
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to seed book entry: {}", bookNode, e);
                                }
                            });
                        }
                    }
                });
            }
            log.info("Book seed data loaded");
        } catch (Exception e) {
            log.error("Exception occurred while loading book seed data", e);
        }
    }

    /**
     * Helper method to get text value or null if field is missing or null
     * @param node JsonNode
     * @param field Field name
     * @return Text value or null
     */
    private static String textOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && !value.isNull() ? value.asText() : null;
    }

    /**
     * Helper method to get int value or default if field is missing or not an int
     * @param node JsonNode
     * @param field Field name
     * @param def Default value
     * @return Int value or default
     */
    private static int intOrDefault(JsonNode node, String field, int def) {
        JsonNode value = node.get(field);
        return value != null && value.canConvertToInt() ? value.asInt() : def;
    }
}
