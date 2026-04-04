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
    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    // equals & hashCode (DO NOT use books)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shelf)) {
            return false;
        }
        Shelf shelf = (Shelf) o;
        return shelfNumber == shelf.shelfNumber && Objects.equals(subject, shelf.subject);
    }
}
