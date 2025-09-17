package me.razorblack.bookwise;

import lombok.extern.slf4j.Slf4j;
import me.razorblack.bookwise.exception.BookNotFoundException;
import me.razorblack.bookwise.exception.BookUnavailableException;
import me.razorblack.bookwise.exception.PatronNotFoundException;
import me.razorblack.bookwise.library.LibraryManager;
import me.razorblack.bookwise.model.Book;
import me.razorblack.bookwise.model.Loan;
import me.razorblack.bookwise.model.Patron;
import me.razorblack.bookwise.util.ConfigFileReader;

import java.util.List;
import java.util.Scanner;

import static me.razorblack.bookwise.constants.BaseConstants.APPLICATION_NAME;
import static me.razorblack.bookwise.constants.BaseConstants.DEFAULT_BORROW_DAYS;

@Slf4j
public class App {
    private static final String APP_NAME;
    private static final LibraryManager libraryManager;
    static {
        libraryManager = LibraryManager.getInstance();
        loadConfiguration();
        APP_NAME = ConfigFileReader.CONFIG.getPropertyValue(APPLICATION_NAME);
        init();
    }

    /**
     * Initialising Application
     */
    private static void init() {
        log.info(APP_NAME + " Library Management System starting.");
        loadSeedData();
    }

    private static void loadSeedData() {
        libraryManager.bootstrapSampleData();
    }

    private static void loadConfiguration() {
        ConfigFileReader.loadConfig();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        log.info("📚 Welcome to " + APP_NAME + " Library Management System");
        int choice;
        do {
            log.info("\n========= MENU =========");
            log.info("1. List all books");
            log.info("2. List books by title");
            log.info("3. List books by author");
            log.info("4. List books by ISBN");
            log.info("5. Add book in library");
            log.info("6. Remove book from library");
            log.info("7. Update book details");
            log.info("8. List all patrons");
            log.info("9. Add patron");
            log.info("10. Update patron details");
            log.info("11. Borrow book");
            log.info("12. Return a book");
            log.info("13. List Available Books");
            log.info("14. List Borrowed Books");
            log.info("0. Exit");
            log.info("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: listAllBooks();
                        break;
                case 2: listBooksByTitle(scanner);
                        break;
                case 3: listBooksByAuthor(scanner);
                        break;
                case 4: listBooksByISBN(scanner);
                        break;
                case 5: addNewBook(scanner);
                        break;
                case 6: removeBookFromLibrary(scanner);
                        break;
                case 7: updateBookDetails(scanner);
                        break;
                case 8: listAllPatrons();
                        break;
                case 9: addNewPatron(scanner);
                        break;
                case 10: updatePatronDetails(scanner);
                        break;
                case 11: borrowBook(scanner);
                        break;
                case 12: returnBook(scanner);
                        break;
                case 13: listAvailableBooks();
                        break;
                case 14: listBorrowedBooks();
                        break;
                case 0: log.info("Exiting... Goodbye!");
                        break;
                default: log.error("Invalid choice. Try again!");
                        break;
            }
        } while (choice != 0);

        scanner.close();
        log.info("Thanks for using " + APP_NAME + "!");
    }

    private static void listBorrowedBooks() {
        List<Book> borrowedBooks = libraryManager.getBookService().getAllBorrowedBooks();
        for (Book book : borrowedBooks) {
            log.info(book.getBookId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    private static void listAvailableBooks() {
        List<Book> availableBooks = libraryManager.getBookService().getAllAvailableBooks();
        for (Book book : availableBooks) {
            log.info(book.getBookId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    private static void updatePatronDetails(Scanner scanner) {
        log.info("Enter Patron ID to update: ");
        String patronId = scanner.nextLine();
        log.info("Enter new name: ");
        String name = scanner.nextLine();
        log.info("Enter new contact: ");
        String contact = scanner.nextLine();

        try {
            libraryManager.getPatronService().updatePatron(patronId, name, contact);
        } catch (PatronNotFoundException bookNotFoundException) {
            log.error("Patron with {} not found for updating.", patronId);
        }
    }

    private static void addNewPatron(Scanner scanner) {
        log.info("Enter patron name: ");
        String name = scanner.nextLine();
        log.info("Enter patron contact: ");
        String contact = scanner.nextLine();

        libraryManager.getPatronService().addPatron(name, contact);
    }

    private static void updateBookDetails(Scanner scanner) {
        log.info("Enter Book ID to update: ");
        String bookId = scanner.nextLine();
        log.info("Enter new title: ");
        String title = scanner.nextLine();
        log.info("Enter new author: ");
        String author = scanner.nextLine();
        log.info("Enter new publication year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            libraryManager.getBookService().updateBook(bookId, title, author, year);
        } catch (BookNotFoundException bookNotFoundException) {
            log.error("Book with {} not found for updating.", bookId);
        }
    }

    private static void removeBookFromLibrary(Scanner scanner) {
        log.info("Enter Book ID to remove: ");
        String bookId = scanner.nextLine();
        libraryManager.getBookService().removeBook(bookId);
    }

    private static void addNewBook(Scanner scanner) {
        log.info("Enter book title: ");
        String title = scanner.nextLine();
        log.info("Enter book author: ");
        String author = scanner.nextLine();
        log.info("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        log.info("Enter publication year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // consume newline

        libraryManager.getBookService().addNewBook(isbn, title, author, year);
    }

    private static void listBooksByISBN(Scanner scanner) {
        log.info("Enter book ISBN");
        String isbn = scanner.nextLine();
        List<Book> books = libraryManager.getBookService().findBookByISNB(isbn);
        for (Book book : books) {
            log.info(book.getBookId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    private static void listBooksByAuthor(Scanner scanner) {
        log.info("Enter author name");
        String author = scanner.nextLine();
        List<Book> books = libraryManager.getBookService().searchByAuthor(author);
        for (Book book : books) {
            log.info(book.getBookId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    private static void listBooksByTitle(Scanner scanner) {
        log.info("Enter book title");
        String title = scanner.nextLine();
        List<Book> books = libraryManager.getBookService().searchByTitle(title);
        for (Book book : books) {
            log.info(book.getBookId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    private static void listAllBooks() {
        List<Book> allBooks = libraryManager.getBookService().getAllBooks();
        for (Book book : allBooks) {
            log.info(book.getBookId() + " - " + book.getTitle() + " (" +
                    (book.isAvailable() ? "Available" : "Borrowed") + ")");
        }
    }

    private static void listAllPatrons() {
        List<Patron> allPatrons = libraryManager.getPatronService().getAllPatrons();
        for (Patron patron : allPatrons) {
            log.info(patron.getId() + " - " + patron.getName());
        }
    }

    private static void borrowBook(Scanner scanner) {
        log.info("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        log.info("Enter Book ID: ");
        String bookId = scanner.nextLine();
        int days = Integer.parseInt(ConfigFileReader.CONFIG.getPropertyValue(DEFAULT_BORROW_DAYS)); // default loan period
        try {
            Loan loan = libraryManager.getLoanService().checkout(bookId, patronId, days);
            log.info("Book borrowed successfully with loan {} !", loan.getLoanId());
        } catch (BookNotFoundException bookNotFoundException) {
            log.error("Book with {} not found for borrowing.", bookId);
        } catch (BookUnavailableException bookUnavailableException) {
            log.error("Book with {} is currently unavailable.", bookId);
        } catch (PatronNotFoundException patronNotFoundException) {
            log.error("Patron with {} not found for borrowing.", patronId);
        }
    }

    private static void returnBook(Scanner scanner) {
        log.info("Do you have load id? (y/n) ");
        String hasLoanId = scanner.nextLine();
        try {
            if (hasLoanId.equalsIgnoreCase("y")) {
                log.info("Enter Loan ID: ");
                String loanId = scanner.nextLine();
                libraryManager.getLoanService().returnBook(loanId);
                return;
            } else {
                log.info("Proceeding with Book ID and Patron ID...");
                log.info("Enter Patron ID: ");
                String patronId = scanner.nextLine();
                log.info("Enter Book ID to return: ");
                String bookId = scanner.nextLine();
                libraryManager.getLoanService().returnBook(patronId, bookId);
            }
        } catch (Exception exception) {
            log.error("Exception occurred during return process: {}", exception.getMessage());
        }
    }
}
