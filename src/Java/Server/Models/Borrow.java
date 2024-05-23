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

    public int getIdBorrow() {
        return idBorrow;
    }
    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }

    public String getIsbn() { return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {return end_date;}
    public void setEnd_date(Date end_date) {this.end_date = end_date; }

    public int getStatus(){return status;}

    public void setStatus(int status){this.status =status;}


    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
