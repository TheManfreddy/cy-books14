package Server.Manager;

import Server.Models.Library;

import java.sql.*;

/**
 * The LibraryManager class provides methods to manage library authentication.
 */
public class LibraryManager {

    /**
     * Validates the login credentials of a library user.
     *
     * @param library The Library object containing the login credentials
     * @return true if the login credentials are valid, false otherwise
     */
    public static boolean validateLogin(Library library) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";  // Database username
        String pass = "";      // Database password

        // SQL query to check login credentials
        String query = "SELECT * FROM library WHERE login = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set query parameters
            stmt.setString(1, library.getLogin());
            stmt.setString(2, library.getPassword());

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User exists
                    return true;
                }
            }
        } catch (SQLException e) {
            // Handle connection and query execution errors
            e.printStackTrace();
        }

        // Return false if the user does not exist or in case of an error
        return false;
    }

}
