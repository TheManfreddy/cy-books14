package methods;

import java.io.Serializable;


public class Library implements Serializable {
    private String login;
    private String password;


    public Library(String login, String password)  {
        this.login = login;
        this.password = password;

    }

    public String getLogin() {
        return login;
    }
    public void setLogin(int ISBN) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }





    @Override
    public String toString() {
        return "methods.Library{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
