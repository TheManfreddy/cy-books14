package Server.Models;

import java.io.Serializable;

/**
 * Represents a book with details such as ISBN, title, author, editor, language, release year, and borrow count.
 * This class implements Serializable to allow book objects to be serialized.
 */
public class Book implements Serializable {
    private String ISBN;
    private String title;
    private String author;
    private String editor;
    private String language;
    private String release_year;
    private int borrowCount;

    /**
     * Constructs a new Book with the specified details.
     *
     * @param ISBN         the International Standard Book Number of the book
     * @param title        the title of the book
     * @param author       the author of the book
     * @param editor       the editor of the book
     * @param language     the language of the book
     * @param release_year the release year of the book
     */
    public Book(String ISBN, String title, String author, String editor, String language, String release_year) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
    }

    /**
     * Constructs a new Book with the specified details and borrow count.
     *
     * @param ISBN         the International Standard Book Number of the book
     * @param title        the title of the book
     * @param author       the author of the book
     * @param editor       the editor of the book
     * @param language     the language of the book
     * @param release_year the release year of the book
     * @param borrowCount  the number of times the book has been borrowed
     */
    public Book(String ISBN, String title, String author, String editor, String language, String release_year, int borrowCount) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
        this.borrowCount = borrowCount;
    }

    /**
     * Gets the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param ISBN the new ISBN of the book
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the editor of the book.
     *
     * @return the editor of the book
     */
    public String getEditor() {
        return editor;
    }

    /**
     * Sets the editor of the book.
     *
     * @param editor the new editor of the book
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * Gets the language of the book.
     *
     * @return the language of the book
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language of the book.
     *
     * @param language the new language of the book
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the release year of the book.
     *
     * @return the release year of the book
     */
    public String getRelease_year() {
        return release_year;
    }

    /**
     * Sets the release year of the book.
     *
     * @param release_year the new release year of the book
     */
    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    /**
     * Gets the number of times the book has been borrowed.
     *
     * @return the borrow count of the book
     */
    public int getBorrowCount() {
        return borrowCount;
    }

    /**
     * Sets the number of times the book has been borrowed.
     *
     * @param borrowCount the new borrow count of the book
     */
    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }
}








