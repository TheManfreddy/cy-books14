package Server.Models;

import java.io.Serializable;


public class Library implements Serializable {
    private String login;
    private String password;


    /**
     * @param login
     * @param password
     */
    public Library(String login, String password)  {
        this.login = login;
        this.password = password;

    }

    /**
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param ISBN
     */
    public void setLogin(int ISBN) {
        this.login = login;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
