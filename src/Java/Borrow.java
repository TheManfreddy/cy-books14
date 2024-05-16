import java.io.Serializable;
import java.util.Date;

public class Borrow implements Serializable {
    private int idBorrow ;
    private int idBook ;
    private String idUser ;
    private int duration ;
    private Date start_date ;
    private Date end_date ;

    public Borrow(int idBorrow, int idBook, String idUser, int duration,Date start_date,Date end_date)  {
        this.idBorrow = idBorrow;
        this.idBook = idBook;
        this.idUser = idUser;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getIdBorrow() {
        return idBorrow;
    }
    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
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
        return "Borrow{" +
                "idBorrow='" + idBorrow + '\'' +
                ", idBook='" + idBook + '\'' +
                ", idUser='" + idUser + '\'' +
                "duration='" + duration + '\'' +
                "start_date='" + start_date + '\'' +
                "end_date='" + end_date + '\'' +
                '}';
    }
}

