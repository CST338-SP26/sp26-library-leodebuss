package Utilities;

import java.util.*;
import java.time.LocalDate;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Leonardo Lopez
 * This is the Library class that will be used to store books as csv files ...
 * Intialized Date: 4/6/26
 */

public class Library {

    private Map<String, Book> books = new HashMap<>();
    private Map<Integer, Shelf> shelves = new HashMap<>();
    private List<Reader> readers = new ArrayList<>();
    private static int libraryCard = 0;

    private String name;

    public Library(String name) {
        this.name = name;
    }

    public Code init(String fileName) {
        try {
            Scanner scanner = new Scanner(new java.io.File(fileName));

            // --- BOOKS SECTION ---
            if (!scanner.hasNextLine()) return Code.BOOK_COUNT_ERROR;
            String bookCountLine = scanner.nextLine().trim();
            int expectedBookCount;
            try {
                expectedBookCount = Integer.parseInt(bookCountLine);
                if (expectedBookCount < 0) return Code.BOOK_COUNT_ERROR;
            } catch (NumberFormatException e) {
                return Code.BOOK_COUNT_ERROR;
            }

            int actualBookRecords = 0;
            while (scanner.hasNextLine() && actualBookRecords < expectedBookCount) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != 6) return Code.BOOK_RECORD_COUNT_ERROR; // must have 6 fields
                actualBookRecords++;
            }

            if (actualBookRecords != expectedBookCount) {
                return Code.BOOK_COUNT_ERROR;
            }

            // --- SHELVES SECTION ---
            if (!scanner.hasNextLine()) return Code.SHELF_COUNT_ERROR;
            String shelfCountLine = scanner.nextLine().trim();
            int expectedShelfCount;
            try {
                expectedShelfCount = Integer.parseInt(shelfCountLine);
                if (expectedShelfCount < 0) return Code.SHELF_COUNT_ERROR;
            } catch (NumberFormatException e) {
                return Code.SHELF_COUNT_ERROR;
            }

            int actualShelfRecords = 0;
            while (scanner.hasNextLine() && actualShelfRecords < expectedShelfCount) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != 2) return Code.SHELF_NUMBER_PARSE_ERROR; // must have 2 fields

                // Check shelf number is integer
                try {
                    Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    return Code.SHELF_NUMBER_PARSE_ERROR;
                }

                actualShelfRecords++;
            }

            if (actualShelfRecords != expectedShelfCount) {
                return Code.SHELF_COUNT_ERROR;
            }

            // TEMP: create some shelves so Main doesn't crash
            addShelf("sci-fi");
            addShelf("Romance");
            addShelf("GrimDark");

            // TEMP: add a dummy reader so getReaderByCard works
            Reader r = new Reader(1, "Test Reader", "123");
            readers.add(r);

            scanner.close();
            return Code.SUCCESS;

        } catch (java.io.FileNotFoundException e) {
            System.out.println("ERROR: File not found " + fileName);
            return Code.FILE_NOT_FOUND_ERROR;
        }
    }
    public Code addBook(Book book) {
        if (book == null) return Code.BOOK_NOT_IN_INVENTORY_ERROR;

        books.put(book.getISBN(), book);
        Shelf shelf = getShelf(book.getSubject());
        if (shelf != null) return shelf.addBook(book);

        return Code.SUCCESS;
    }

    public int listBooks() {
        int total = 0;
        for (Shelf shelf : shelves.values()) {
            for (Map.Entry<Book, Integer> entry : shelf.getBooks().entrySet()) {
                Book book = entry.getKey();
                int count = entry.getValue();
                total += count;
                System.out.println(count + " copies of " + book);
            }
        }
        return total;
    }

    public Code checkOutBook(Reader reader, Book book) {
        if (!readers.contains(reader)) return Code.READER_NOT_IN_LIBRARY_ERROR;
        if (reader.getBookCount() >= 3) return Code.BOOK_LIMIT_REACHED_ERROR;
        if (!books.containsKey(book.getISBN())) return Code.BOOK_NOT_IN_INVENTORY_ERROR;

        Shelf shelf = getShelf(book.getSubject());
        if (shelf == null) return Code.SHELF_EXISTS_ERROR;

        Code result = reader.addBook(book);
        if (result != Code.SUCCESS) return result;

        return shelf.removeBook(book);
    }

    public Code returnBook(Reader reader, Book book) {
        if (!readers.contains(reader)) return Code.READER_NOT_IN_LIBRARY_ERROR;
        if (!reader.hasBook(book)) return Code.READER_DOESNT_HAVE_BOOK_ERROR;

        Shelf shelf = getShelf(book.getSubject());
        if (shelf == null) return Code.SHELF_EXISTS_ERROR;

        Code result = reader.removeBook(book);
        if (result != Code.SUCCESS) return result;

        return shelf.addBook(book);
    }

    public Book getBookByISBN(String isbn) {
        return books.get(isbn);
    }

    public int listShelves() { return listShelves(false); }

    public int listShelves(boolean showBooks) {
        for (Shelf shelf : shelves.values()) {
            if (showBooks) {
                System.out.println(shelf.listBooks());
            } else {
                System.out.println(shelf);
            }
        }
        return shelves.size();
    }

    public Code addShelf(String shelfSubject) {
        Shelf shelf = new Shelf(shelves.size() + 1, shelfSubject);
        return addShelf(shelf);
    }

    public Code addShelf(Shelf shelf) {
        for (Shelf s : shelves.values()) {
            if (s.getSubject().equalsIgnoreCase(shelf.getSubject())) return Code.SHELF_EXISTS_ERROR;
        }
        int nextNumber = shelves.keySet().stream().max(Integer::compare).orElse(0) + 1;
        shelf.setShelfNumber(nextNumber);
        shelves.put(nextNumber, shelf);

        for (Book book : books.values()) {
            if (book.getSubject().equalsIgnoreCase(shelf.getSubject())) shelf.addBook(book);
        }
        return Code.SUCCESS;
    }

    public Shelf getShelf(Integer shelfNumber) { return shelves.get(shelfNumber); }
    public Shelf getShelf(String subject) {
        for (Shelf s : shelves.values()) {
            if (s.getSubject().equalsIgnoreCase(subject)) return s;
        }
        return null;
    }

    public int listReaders() {
        for (Reader reader : readers) System.out.println(reader);
        return readers.size();
    }

    public int listReaders(boolean showBooks) {
        for (Reader reader : readers) {
            if (showBooks) System.out.println(reader + " has: " + reader.getBooks());
            else System.out.println(reader);
        }
        return readers.size();
    }

    public Reader getReaderByCard(int cardNumber) {
        for (Reader r : readers) if (r.getCardNumber() == cardNumber) return r;
        return null;
    }

    public Code addReader(Reader reader) {
        if (readers.contains(reader)) return Code.READER_ALREADY_EXISTS_ERROR;
        for (Reader r : readers) if (r.getCardNumber() == reader.getCardNumber()) return Code.READER_CARD_NUMBER_ERROR;

        readers.add(reader);
        if (reader.getCardNumber() > libraryCard) libraryCard = reader.getCardNumber();
        return Code.SUCCESS;
    }

    public Code removeReader(Reader reader) {
        if (!readers.contains(reader)) return Code.READER_NOT_IN_LIBRARY_ERROR;
        if (!reader.getBooks().isEmpty()) return Code.READER_STILL_HAS_BOOKS_ERROR;

        readers.remove(reader);
        return Code.SUCCESS;
    }

    public static int convertInt(String s, Code code) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return code.getCode();
        }
    }

    public static LocalDate convertDate(String date, Code code) {
        return LocalDate.of(1970, 1, 1);
    }
    public static int getLibraryCardNumber() { return libraryCard + 1; }

    public String getName() {
        return name;
    } // oops almost forgot
}