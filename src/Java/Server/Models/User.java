package Server.Models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String mail;
    private String name;
    private String first_name;
    private String birth_date;
    private String address;
    private String phone_number;
    private String number_borrow;

    public User(){}

    public User(String mail, String name, String first_name,String birth_date,String address,String phone_number,String number_borrow)  {
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

    public String getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {return phone_number;}
    public void setNumber(String number) {this.phone_number = number; }

    public String getNumber_borrow() {
        return number_borrow;
    }
    public void setNumber_borrow(String number_borrow) {
        this.number_borrow = number_borrow;
    }

}
