package Cy_book.test;
import java.io.Serializable;


public class User implements Serializable {
    private String mail;
    private String name;
    private String firstName;

    public User(String mail, String name, String firstName) {
        this.mail = mail;
        this.name = name;
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
