package methods;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String mail;
    private String name;
    private String first_name;
    private Date birth_date;
    private String address;
    private int phone_number;
    private int number_borrow;

    public User(String mail, String name, String first_name, Date birth_date, int phone_number,int number_borrow,String address)  {
        this.mail = mail;
        this.name = name;
        this.first_name = first_name;
        this.birth_date = birth_date;
        this.address = address;
        this.phone_number = phone_number;
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

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {return phone_number;}
    public void setMail(int number) {this.phone_number = number; }

    public int getNumber_borrow() {
        return number_borrow;
    }
    public void setNumber_borrow(int number_borrow) {
        this.number_borrow = number_borrow;
    }



    @Override
    public String toString() {
        return "methods.User{" +
                "mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                "address='" + address + '\'' +
                "phone_number='" + phone_number + '\'' +
                "number_borrow='" + number_borrow + '\'' +
                '}';
    }
}
