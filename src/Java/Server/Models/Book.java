package Server.Models;

import java.io.Serializable;

public class Book implements Serializable {
    private int ISBN;
    private String title;
    private String author;
    private String editor;
    private String language;
    private int release_year;


    public Book(int ISBN, String title, String author,String editor,String language,int release_year)  {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.language = language;
        this.release_year = release_year;
    }

    public int getISBN() {
        return ISBN;
    }
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
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

    public int getRelease_year() {return release_year;}
    public void setRelease_year(int release_year) {this.release_year = release_year; }

}