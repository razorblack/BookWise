package me.razorblack.bookwise.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {
    @NonNull
    private final String isbn;
    @NonNull
    private String title;
    @NonNull
    private String author;
    private int publicationYear;
    private boolean available = true;
}
