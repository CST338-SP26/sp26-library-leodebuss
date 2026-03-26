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

    // Getters and Setters
    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public int getBookCount() {
        return books.size();
    }

    // addBook
    public Code addBook(Book book) {
        if (book == null) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR; // or another error if defined
        }

        if (books.contains(book)) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }

        books.add(book);
        return Code.SUCCESS;
    }

    // removeBook
    public Code removeBook(Book book) {
        if (!books.contains(book)) {
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        if (books.remove(book)) {
            return Code.SUCCESS;
        }

        return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
    }

    // hasBook
    public boolean hasBook(Book book) {
        return books.contains(book);
    }

    // equals (compare ALL fields except books)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reader)) return false;

        Reader other = (Reader) obj;

        return this.cardNumber == other.cardNumber &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.phone, other.phone);
    }

    // hashCode (same fields as equals)
    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, name, phone);
    }

    // toString
    @Override
    public String toString() {
        StringBuilder bookList = new StringBuilder();

        for (int i = 0; i < books.size(); i++) {
            bookList.append(books.get(i));
            if (i < books.size() - 1) {
                bookList.append(", ");
            }
        }

        return name + " (#" + cardNumber + ") has checked out {" + bookList + "}";
    }
}
