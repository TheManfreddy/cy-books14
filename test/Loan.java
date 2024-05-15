package Cy_book.test;

import java.io.Serializable;
import java.util.Date;


public class Loan implements Serializable {
    private int idLoan ;
    private int idBook ;
    private String idUser ;
    private int duration ;
    private Date start_date ;
    private Date end_date ;

    public Loan(int idLoan, int idBook, String idUser, int duration,Date start_date,Date end_date)  {
        this.idLoan = idLoan;
        this.idBook = idBook;
        this.idUser = idUser;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getIdLoan() {
        return idLoan;
    }
    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }

    public int getIdBook() { return idBook;
    }
    public void setIdBook(int idBook) {
        this.idBook = idBook;
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
    public void setEnd_date(Date stock) {this.end_date = end_date; }



    @Override
    public String toString() {
        return "Loan{" +
                "idLoan='" + idLoan + '\'' +
                ", idBook='" + idBook + '\'' +
                ", idUser='" + idUser + '\'' +
                "duration='" + duration + '\'' +
                "start_date='" + start_date + '\'' +
                "end_date='" + end_date + '\'' +
                '}';
    }
}

