package Server.Models;

import java.io.Serializable;

/**
 * Represents a user with personal information such as email, name, first name, birth date, address, phone number, and number of borrows.
 * This class implements Serializable to allow user objects to be serialized.
 */
public class User implements Serializable {
    private String mail;
    private String name;
    private String first_name;
    private String birth_date;
    private String address;
    private String phone_number;
    private String number_borrow;

    /**
     * Default constructor for User class.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified personal information.
     *
     * @param mail          the email of the user
     * @param name          the last name of the user
     * @param first_name    the first name of the user
     * @param birth_date    the birth date of the user
     * @param address       the address of the user
     * @param phone_number  the phone number of the user
     * @param number_borrow the number of borrows of the user
     */
    public User(String mail, String name, String first_name, String birth_date, String address, String phone_number, String number_borrow) {
        this.mail = mail;
        this.name = name;
        this.first_name = first_name;
        this.birth_date = birth_date;
        this.address = address;
        this.phone_number = phone_number;
        this.number_borrow = number_borrow;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the email of the user.
     *
     * @param mail the new email of the user
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the last name of the user.
     *
     * @param name the new last name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Sets the first name of the user.
     *
     * @param first_name the new first name of the user
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Gets the birth date of the user.
     *
     * @return the birth date of the user
     */
    public String getBirth_date() {
        return birth_date;
    }

    /**
     * Sets the birth date of the user.
     *
     * @param birth_date the new birth date of the user
     */
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * Gets the address of the user.
     *
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address the new address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getNumber() {
        return phone_number;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param number the new phone number of the user
     */
    public void setNumber(String number) {
        this.phone_number = number;
    }

    /**
     * Gets the number of borrows of the user.
     *
     * @return the number of borrows of the user
     */
    public String getNumber_borrow() {
        return number_borrow;
    }

    /**
     * Sets the number of borrows of the user.
     *
     * @param number_borrow the new number of borrows of the user
     */
    public void setNumber_borrow(String number_borrow) {
        this.number_borrow = number_borrow;
    }
}
