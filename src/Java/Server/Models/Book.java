
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

    public Book(String ISBN, String title, String author,String editor,String language,String release_year, int borrowCount)  {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
        this.borrowCount = borrowCount;
    }

    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public  String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }
    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRelease_year() {return release_year;}
    public void setRelease_year(String release_year) {this.release_year = release_year; }

    public int getBorrowCount() {return borrowCount;}
    public void setBorrowCount(int borrowCount) {this.borrowCount = borrowCount;}
}








