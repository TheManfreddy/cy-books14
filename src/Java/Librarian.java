import java.sql.*;
import java.util.Date;

public class Librarian {

    public static String searchBook (String search) {


        String query = "((bib.author all " + "\"" + search + "\"" + ") or (bib.title all " + "\"" + search + "\"" + ")) and (bib.doctype all \"a\")";
        return query;


    }

    public static void searchUser(String mail) throws SQLException{

            String query = "SELECT * FROM  user WHERE mail = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
                    PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, mail);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String mail1 = rs.getString("mail");
                        String name = rs.getString("name");
                        String first_name = rs.getString("first_name");
                        String date_birth = rs.getString("date_birth");
                        String adress = rs.getString("adress");
                        String phonenumber = rs.getString("phonenumber");
                        int number_borrow = rs.getInt("number_borrow");

                        // Print the results
                        System.out.println("mail: " + mail);
                        System.out.println("Name: " + name);
                        System.out.println("First Name: " + first_name);
                        System.out.println("Date of Birth: " + date_birth);
                        System.out.println("Address: " + adress);
                        System.out.println("Phone Number: " + phonenumber);
                        System.out.println("Number Borrow: " + number_borrow);
                    }
                }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void registerUser(String mail, String name, String first_name, String date_birth, String adress, String phonenumber, int number_borrow) throws SQLException {

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
            String query = "INSERT INTO user  (mail, name, first_name, date_birth, adress, phonenumber, number_borrow)  VALUES (?, ?, ?, ?, ?, ?, ?) ";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                // Attribution des valeurs au paramètres
                stmt.setString(1, mail);
                stmt.setString(2, name);
                stmt.setString(3, first_name);
                stmt.setString(4, date_birth);
                stmt.setString(5, adress);
                stmt.setString(6, phonenumber);
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

    public static void modifyInformation(String mail, String name, String first_name,String date_birth, String adress,String phonenumber) throws SQLException {
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
            } if (date_birth != null) {
                String query3 = "UPDATE user SET date_birth = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query3);
                stmt.setString(1, date_birth);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("La date de naissance a été modifiée avec succès.");
                } else {
                    System.out.println("Échec modification de la date de naissance.");
                }
            } if (adress != null) {
                String query4 = "UPDATE user SET adress = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query4);
                stmt.setString(1, adress);
                stmt.setString(2, mail);
                rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("L'adresse a été modifiée avec succès.");
                } else {
                    System.out.println("Échec modification de l'adresse.");
                }
            } if (phonenumber != null) {
                String query5 = "UPDATE user SET phonenumber = ? WHERE mail = ?";
                PreparedStatement stmt = conn.prepareStatement(query5);
                stmt.setString(1, phonenumber);
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

    public static void main(String[] args) throws SQLException {
        modifyInformation("albertroger@gmail.com","Branda",null,null,"12 rue des merles chanteurs,Paris",null);

    }
}

