package Utilities;

import java.time.LocalDate;

/**
 * @author Leonardo Lopez
 * This is a plain old java object (POJO) that represents a Book
 * It will be used in the larger Library Project
 *
 */

public class Book {

    public static final int ISBN_ = 0;
    public static final int TITLE_ = 1;
    public static final int SUBJECT_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int AUTHOR_ = 4;
    public static final int DUE_DATE_ = 5;

    private String isbn;
    private String title;
    private String subject;
    private int pageCount;
    private String author;
    private LocalDate dueDate;

}
