package Cy_book.test;
import java.io.Serializable;


public class Book implements Serializable {
    private int ISBN;
    private String title;
    private String kind;
    private String author;
    private String editor;
    private String picture;
    private String language;
    private int release_year;
    private int stock;

    public Book(int ISBN, String title, String kind, String author,String editor,String picture,String language,int release_year,int stock)  {
        this.ISBN = ISBN;
        this.title = title;
        this.kind = kind;
        this.author = author;
        this.editor = editor;
        this.picture = picture;
        this.language = language;
        this.release_year = release_year;
        this.stock = stock;
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

    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
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

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRelease_year() {return release_year;}
    public void setRelease_year(int release_year) {this.release_year = release_year; }
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock; }



    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", kind='" + kind + '\'' +
                "author='" + author + '\'' +
                "editor='" + editor + '\'' +
                "picture='" + picture + '\'' +
                "language='" + language + '\'' +
                "author='" + author + '\'' +
                "release_year='" + release_year + '\'' +
                '}';
    }
}
