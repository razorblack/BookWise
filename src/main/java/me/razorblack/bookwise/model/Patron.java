package me.razorblack.bookwise.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Patron {
    @NonNull
    private final String id;
    @NonNull
    private String name;
    private String contact;
    // Borrowing history (loan ids or Loan objects)
    private final List<String> borrowingHistory = new ArrayList<>();
}
