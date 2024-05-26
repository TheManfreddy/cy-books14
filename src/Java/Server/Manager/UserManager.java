package Server.Manager;

import Server.Models.Borrow;
import Server.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Server.Manager.BorrowManager.historyBorrow;

/**
 * The UserManager class provides methods to manage user registration, modification,
 * searching, and display of user information.
 */
public class UserManager {

    /**
     * Registers a new user with the provided information into the database.
     *
     * @param mail          The email of the user
     * @param name          The name of the user
     * @param first_name    The first name of the user
     * @param birth_date    The birth date of the user
     * @param address       The address of the user
     * @param phone_number  The phone number of the user
     * @param number_borrow The number of books currently borrowed by the user
     * @throws SQLException If a database access error occurs
     */
    public static void registerUser(String mail, String name, String first_name, String birth_date, String address, String phone_number, int number_borrow) throws SQLException {
        // Database connection establishment
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "")) {
            String query = "INSERT INTO user (mail, name, first_name, birth_date, address, phone_number, number_borrow) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                // Setting values for query parameters
                stmt.setString(1, mail);
                stmt.setString(2, name);
                stmt.setString(3, first_name);
                stmt.setString(4, birth_date);
                stmt.setString(5, address);
                stmt.setString(6, phone_number);
                stmt.setInt(7, number_borrow);

                // Executing the query to insert the user into the database
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User added successfully!");
                } else {
                    System.out.println("Failed to add user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies user information based on the provided parameters.
     *
     * @param mail         The email of the user
     * @param name         The new name of the user
     * @param first_name   The new first name of the user
     * @param birth_date   The new birth date of the user
     * @param address      The new address of the user
     * @param phone_number The new phone number of the user
     * @throws SQLException If a database access error occurs
     */
    public static void modifyInformation(String mail, String name, String first_name, String birth_date, String address, String phone_number) throws SQLException {
        // Database connection establishment
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "")) {
            int rowsInserted;
            // Update name if provided
            if (name != null) {
                String query1 = "UPDATE user SET name = ? WHERE mail = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query1)) {
                    stmt.setString(1, name);
                    stmt.setString(2, mail);
                    rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name modified successfully.");
                    } else {
                        System.out.println("Failed to modify name.");
                    }
                }
            }
            // Update first name if provided
            if (first_name != null) {
                String query2 = "UPDATE user SET first_name = ? WHERE mail = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query2)) {
                    stmt.setString(1, first_name);
                    stmt.setString(2, mail);
                    rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("First name modified successfully.");
                    } else {
                        System.out.println("Failed to modify first name.");
                    }
                }
            }
            // Update birth date if provided
            if (birth_date != null) {
                String query3 = "UPDATE user SET birth_date = ? WHERE mail = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query3)) {
                    stmt.setString(1, birth_date);
                    stmt.setString(2, mail);
                    rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Birth date modified successfully.");
                    } else {
                        System.out.println("Failed to modify birth date.");
                    }
                }
            }
            // Update address if provided
            if (address != null) {
                String query4 = "UPDATE user SET address = ? WHERE mail = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query4)) {
                    stmt.setString(1, address);
                    stmt.setString(2, mail);
                    rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Address modified successfully.");
                    } else {
                        System.out.println("Failed to modify address.");
                    }
                }
            }
            // Update phone number if provided
            if (phone_number != null) {
                String query5 = "UPDATE user SET phone_number = ? WHERE mail = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query5)) {
                    stmt.setString(1, phone_number);
                    stmt.setString(2, mail);
                    rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Phone number modified successfully.");
                    } else {
                        System.out.println("Failed to modify phone number.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for a user based on the provided email.
     *
     * @param mail The email of the user to search for
     * @return The User object corresponding to the provided email
     * @throws SQLException If a database access error occurs
     */
    public static User searchUser(String mail) throws SQLException {
        String query = "SELECT * FROM  user WHERE mail = ?";
        User user = new User();
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
                    user = new User(mail, name, first_name, birth_date, address, phone_number, number_borrow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Displays the user details along with their borrowing history.
     *
     * @param mail The email of the user to display
     * @return A list containing the user details and their borrowing history
     * @throws SQLException If a database access error occurs
     */
    public static List<Object> displayUser(String mail) throws SQLException {
        List<Object> userBorrows = new ArrayList<>();
        User user = searchUser(mail); // Retrieve user details
        userBorrows.add(user); // Add user details to the list

        List<Borrow> listborrow = historyBorrow(mail); // Retrieve borrowing history for the user
        for (Borrow borrow : listborrow) {
            userBorrows.add(borrow); // Add each borrowing record to the list
        }
        return (userBorrows); // Return the list containing user details and borrowing history
    }

    /**
     * Displays the list of all users in the database.
     *
     * @return A list containing all users in the database
     */
    public static List<User> displayUserList() {
        List<User> UserList = new ArrayList<>();
        String query = "SELECT mail FROM  user";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mail = rs.getString("mail");
                    User user = searchUser(mail); // Retrieve user details
                    UserList.add(user); // Add user details to the list
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (UserList); // Return the list containing all users
    }

    /**
     * Displays the list of users who have overdue book borrowings.
     *
     * @return A list containing users with overdue borrowings
     */
    public static List<User> displayUserBorrowLateList() {
        List<User> UserList = new ArrayList<>();
        String query = "SELECT idUser FROM borrow WHERE duration>30 AND status=?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, 0);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idUser = rs.getString("idUser");
                    User user = searchUser(idUser); // Retrieve user details
                    UserList.add(user); // Add user details to the list
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (UserList); // Return the list containing users with overdue borrowings
    }

    /**
     * Checks if a user with the given email already exists in the database.
     *
     * @param mail The email to check for existence
     * @return True if the email exists, otherwise false
     */
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
        return exists; // Return whether the email exists in the database
    }
}


