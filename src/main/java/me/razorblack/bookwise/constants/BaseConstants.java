package me.razorblack.bookwise.constants;

import java.io.File;

/**
 * Class to hold base constants used across the application
 */
public class BaseConstants {
    // Path & general tokens
    public static final String SEPARATOR = File.separator; // must be defined before use
    public static final String COMMA = ",";
    public static final String EMPTY_STRING = "";

    // Project structure
    public static final String SRC = "src";
    public static final String MAIN = "main";
    public static final String RESOURCES = "resources";
    public static final String CONFIG_FILE_NAME = "config.properties";

    // App config keys
    public static final String APPLICATION_NAME = "app.name";
    public static final String APP_DATA_STORAGE = "app.dataStorage";
    public static final String IN_MEMORY = "InMemory";
    public static final String DEFAULT_BORROW_DAYS = "app.defaultBorrowDays";
    public static final String ACTIVE_BRANCHES = "app.activeBranches";

    // Seed data files
    public static final String DATA = "data";
    public static final String BOOKS = "books";
    public static final String PATRONS = "patrons";
    public static final String JSON_EXTENSION = ".json";
    public static final String SEED_PATRON_FILE_NAME = PATRONS + JSON_EXTENSION;
    public static final String SEED_BOOKS_FILE_NAME = BOOKS + JSON_EXTENSION;
    public static final String SEED_PATRON_FILE_PATH = DATA + SEPARATOR + SEED_PATRON_FILE_NAME;
    public static final String SEED_BOOKS_FILE_PATH = DATA + SEPARATOR + SEED_BOOKS_FILE_NAME;
}
