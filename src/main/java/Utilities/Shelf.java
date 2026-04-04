package Utilities;

import java.util.*;

/**
 * @author Leonardo Lopez
 * This is a shelf class that will be used to store books as csv files ...
 * Intialized Date: 4/3/26
 */

public class Shelf {
    // Constant Fields
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    // No-arg constructor (must initialize books!)
    public Shelf() {
        this.books = new HashMap<>();
    }

    // Parameterized constructor
    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        this.books = new HashMap<>();
    }

    // Getters and Setters
}
