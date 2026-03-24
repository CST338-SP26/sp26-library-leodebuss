package Utilities;

import java.util.*;

/**
 * @author Leonardo Lopez
 * This is a reader class that will be used to read ...
 * Intialized Date: 3/24/26
 */


public class Reader {
    // Constant fields
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private ArrayList<Book> books;

    // Constructor
    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        this.books = new ArrayList<>();
    }
}
