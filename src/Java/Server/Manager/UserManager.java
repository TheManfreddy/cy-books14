package Server.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Server.Data.APIBNF.retrieveBook_isbn;
import static Server.Manager.BorrowManager.historyBorrow;

public class UserManager {
    public static void registerUser(String mail, String name, String first_name, String birth_date, String address, String phone_number, int number_borrow) throws SQLException {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
            String query = "INSERT INTO user  (mail, name, first_name, birth_date, address, phone_number, number_borrow)  VALUES (?, ?, ?, ?, ?, ?, ?) ";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                // Attribution des valeurs au paramètres
                stmt.setString(1, mail);
                stmt.setString(2, name);
                stmt.setString(3, first_name);
                stmt.setString(4, birth_date);
                stmt.setString(5, address);
                stmt.setString(6, phone_number);
                stmt.setInt(7, number_borrow);

                // Exécuter la requête pour insérer l'utilisateur dans la base de données
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Utilisateur ajouté avec succès !");
                } else {
                    System.out.println("Échec de l'ajout de l'utilisateur.");
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void modifyInformation(String mail, String name, String first_name,String birth_date, String address,String phone_number) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
            int rowsInserted;
            if (name != null) {
                String query1 = "UPDATE user SET name = ? WHERE mail = ?";
                System.out.println(query1);
                PreparedStatement stmt = conn.prepareStatement(query1);
                stmt.setString(1, name);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le nom a été modifié avec succès.");
                } else {
                    System.out.println("Échec modification du nom.");
                }

            }if (first_name != null) {
                String query2 = "UPDATE user SET first_name = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query2);
                stmt.setString(1, first_name);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le nom de famille a été modifié avec succès.");
                } else {
                    System.out.println("Échec modification du nom de famille.");
                }
            } if (birth_date != null) {
                String query3 = "UPDATE user SET birth_date = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query3);
                stmt.setString(1, birth_date);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("La date de naissance a été modifiée avec succès.");
                } else {
                    System.out.println("Échec modification de la date de naissance.");
                }
            } if (address != null) {
                String query4 = "UPDATE user SET address = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query4);
                stmt.setString(1, address);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("L'adresse a été modifiée avec succès.");
                } else {
                    System.out.println("Échec modification de l'adresse.");
                }
            } if (phone_number != null) {
                String query5 = "UPDATE user SET phone_number = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query5);
                stmt.setString(1, phone_number);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le numéro de téléphone a été modifié avec succès.");
                } else {
                    System.out.println("Échec modification du numéro de téléphone.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> searchUser(String mail) throws SQLException{
        List<String> user =new ArrayList<>();
        String query = "SELECT * FROM  user WHERE mail = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mail);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mail = rs.getString("mail");
                    String name = rs.getString("name");
                    String first_name = rs.getString("first_name");
                    String birth_date = rs.getString("birth_date");
                    String address = rs.getString("address");
                    String phone_number = rs.getString("phone_number");
                    String number_borrow = String.valueOf(rs.getInt("number_borrow"));

                    user= new User(mail,name,first_name,birth_date,address,phone_number,number_borrow);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return(user);
    }
    public static List<Object> displayUser(String mail) throws SQLException {

        List<Object> userBorrows = new ArrayList<>();
        User user= UserManager.searchUser(mail);
        userBorrows.add(user);
        List<Borrow> listborrow = historyBorrow(mail);
        for (Borrow borrow : listborrow) {
            String isbn= borrow.getIsbn();
            Book book = retrieveBook_isbn(isbn);
            userBorrows.add(book);
        }
        return(user);
    }
    public static List<User> displayUserList() {
        List<User> UserList = new ArrayList<>();
        String query = "SELECT mail FROM  user";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mail = rs.getString("mail");
                    User user = searchUser(mail);
                    System.out.println(" ");
                    UserList.add(user);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (UserList);
    }
    public static List<List<String>> displayUserBorrowLateList() {
        List<List<String>> UserList = new ArrayList<>();
        String query = "SELECT idUser FROM borrow WHERE duration>30 AND status=?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, 0);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idUser = rs.getString("idUser");
                    List user = searchUser(idUser);
                    System.out.println(" ");
                    UserList.add(user);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (UserList);
    }
    public static boolean isUserEmailExists(String mail) {
        boolean exists = false;
        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";
        String password = "";

        String query = "SELECT COUNT(*) FROM user WHERE mail = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
}
