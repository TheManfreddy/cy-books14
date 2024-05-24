package Server.Models;

import java.io.Serializable;

/**
 * Represents a library with login credentials.
 * This class implements Serializable to allow library objects to be serialized.
 */
public class Library implements Serializable {
    private String login;
    private String password;

    /**
     * Constructs a new Library with the specified login credentials.
     *
     * @param login    the login username of the library
     * @param password the login password of the library
     */
    public Library(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Gets the login username of the library.
     *
     * @return the login username of the library
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login username of the library.
     *
     * @param login the new login username of the library
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the login password of the library.
     *
     * @return the login password of the library
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the login password of the library.
     *
     * @param password the new login password of the library
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
