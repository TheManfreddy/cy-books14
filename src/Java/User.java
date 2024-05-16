import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String mail;
    private String name;
    private String firstName;
    private Date date_birth;
    private String adress;
    private int phonenumber;
    private int number_borrow;

    public User(String mail, String name, String firstName, Date date_birth, int number,int number_borrow,String adress)  {
        this.mail = mail;
        this.name = name;
        this.firstName = firstName;
        this.date_birth = date_birth;
        this.adress = adress;
        this.phonenumber = number;
        this.number_borrow = number_borrow;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDate_birth() {
        return date_birth;
    }
    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getNumber() {return phonenumber;}
    public void setMail(int number) {this.phonenumber = number; }

    public int getNumber_borrow() {
        return number_borrow;
    }
    public void setNumber_borrow(int number_borrow) {
        this.number_borrow = number_borrow;
    }



    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", date_birth='" + date_birth + '\'' +
                "adress='" + adress + '\'' +
                "number='" + phonenumber + '\'' +
                "number_borrow='" + number_borrow + '\'' +
                '}';
    }
}
