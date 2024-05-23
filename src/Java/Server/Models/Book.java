package Server.Models;

import java.io.Serializable;

public class Book implements Serializable {
    private String ISBN;
    private String title;
    private String author;
    private String editor;
    private String language;
    private String release_year;
    private int borrowCount;


    public Book(String ISBN, String title, String author,String editor,String language,String release_year)  {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
    }

    /**
     * @param ISBN
     * @param title
     * @param author
     * @param editor
     * @param language
     * @param release_year
     * @param borrowCount
     */
    public Book(String ISBN, String title, String author,String editor,String language,String release_year, int borrowCount)  {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
        this.borrowCount = borrowCount;
    }

    /**
     * @return
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return
     */
    public  String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return
     */
    public String getEditor() {
        return editor;
    }

    /**
     * @param editor
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return
     */
    public String getRelease_year() {return release_year;}

    /**
     * @param release_year
     */
    public void setRelease_year(String release_year) {this.release_year = release_year; }

    /**
     * @return
     */
    public int getBorrowCount() {return borrowCount;}

    /**
     * @param borrowCount
     */
    public void setBorrowCount(int borrowCount) {this.borrowCount = borrowCount;}

}