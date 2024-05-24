package Server.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a borrowing record with details such as borrow ID, ISBN, user ID, duration, start date, end date, status, and color.
 * This class implements Serializable to allow borrow objects to be serialized.
 */
public class Borrow implements Serializable {
    private int idBorrow;
    private String isbn;
    private String idUser;
    private int duration;
    private Date start_date;
    private Date end_date;
    private int status;
    private String color;

    /**
     * Constructs a new Borrow with the specified details.
     *
     * @param idBorrow  the unique ID of the borrow record
     * @param isbn      the ISBN of the borrowed book
     * @param idUser    the ID of the user who borrowed the book
     * @param duration  the duration of the borrow in days
     * @param start_date the start date of the borrow
     * @param end_date  the end date of the borrow
     * @param status    the status of the borrow
     */
    public Borrow(int idBorrow, String isbn, String idUser, int duration, Date start_date, Date end_date, int status) {
        this.idBorrow = idBorrow;
        this.isbn = isbn;
        this.idUser = idUser;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.color = null;
    }

    /**
     * Gets the ID of the borrow record.
     *
     * @return the ID of the borrow record
     */
    public int getIdBorrow() {
        return idBorrow;
    }

    /**
     * Sets the ID of the borrow record.
     *
     * @param idBorrow the new ID of the borrow record
     */
    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }

    /**
     * Gets the ISBN of the borrowed book.
     *
     * @return the ISBN of the borrowed book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the borrowed book.
     *
     * @param isbn the new ISBN of the borrowed book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the ID of the user who borrowed the book.
     *
     * @return the ID of the user who borrowed the book
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Sets the ID of the user who borrowed the book.
     *
     * @param idUser the new ID of the user who borrowed the book
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * Gets the duration of the borrow in days.
     *
     * @return the duration of the borrow in days
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the borrow in days.
     *
     * @param duration the new duration of the borrow in days
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the start date of the borrow.
     *
     * @return the start date of the borrow
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * Sets the start date of the borrow.
     *
     * @param start_date the new start date of the borrow
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * Gets the end date of the borrow.
     *
     * @return the end date of the borrow
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * Sets the end date of the borrow.
     *
     * @param end_date the new end date of the borrow
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * Gets the status of the borrow.
     *
     * @return the status of the borrow
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the borrow.
     *
     * @param status the new status of the borrow
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the color associated with the borrow.
     *
     * @return the color associated with the borrow
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Sets the color associated with the borrow.
     *
     * @param color the new color associated with the borrow
     */
    public void setColor(String color) {
        this.color = color;
    }
}
