package Server.Models;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Borrow implements Serializable {
    private int idBorrow ;
    private String isbn ;
    private String idUser ;
    private int duration ;
    private Date start_date ;
    private Date end_date ;
    private int status;

    private String color;


    /**
     * @param idBorrow
     * @param isbn
     * @param idUser
     * @param duration
     * @param start_date
     * @param end_date
     * @param status
     */
    public Borrow(int idBorrow, String isbn, String idUser, int duration, Date start_date, Date end_date, int status)  {
        this.idBorrow = idBorrow;
        this.isbn = isbn;
        this.idUser = idUser;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status =status;
        this.color = null;
    }

    /**
     * @return
     */
    public int getIdBorrow() {
        return idBorrow;
    }

    /**
     * @param idBorrow
     */
    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }

    /**
     * @return
     */
    public String getIsbn() { return isbn;
    }

    /**
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return
     */
    public Date getEnd_date() {return end_date;}

    /**
     * @param end_date
     */
    public void setEnd_date(Date end_date) {this.end_date = end_date; }

    /**
     * @return
     */
    public int getStatus(){return status;}

    /**
     * @param status
     */
    public void setStatus(int status){this.status =status;}


    /**
     * @return
     */
    public String getColor() {
        return this.color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

}
