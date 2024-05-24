package Server.Manager;

import Server.Models.Borrow;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages operations related to borrowing books.
 */
public class BorrowManager {

    /**
     * Registers a new borrow entry in the database.
     *
     * @param isbn   the ISBN of the book being borrowed
     * @param idUser the ID of the user borrowing the book
     */
    public static void registerBorrow(String isbn, String idUser) {
        // Get the current date
        LocalDate start_date = LocalDate.now();
        // Calculate the end date (30 days from the start date)
        LocalDate end_date = start_date.plusDays(30);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Prepare the SQL INSERT INTO statement
            String sql = "INSERT INTO Borrow (isbn,idUser,duration,start_date,end_date,status) VALUES (?, ?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);

            // Set parameter values
            pstmt.setString(1, isbn);
            pstmt.setString(2, idUser);
            pstmt.setInt(3, 0); // Duration initially set to 0
            pstmt.setDate(4, java.sql.Date.valueOf(start_date));
            pstmt.setDate(5, java.sql.Date.valueOf(end_date));
            pstmt.setInt(6, 0); // Status initially set to 0

            // Execute the INSERT INTO query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Insertion successful!");
            } else {
                System.out.println("Insertion failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Updates the duration of ongoing borrows for a specific user.
     *
     * @param idUser the ID of the user
     */
    public static void updateBorrow(String idUser) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";
        String password = "";

        // SQL statements
        String statusSql = "SELECT isbn FROM Borrow WHERE idUser=?";
        String selectSql = "SELECT start_date FROM Borrow WHERE idUser=? AND status=0 AND isbn=?";
        String updateSql = "UPDATE Borrow SET duration = ? WHERE idUser=? AND status=0 AND isbn=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement isbnStmt = conn.prepareStatement(statusSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Retrieve the ISBNs of books borrowed by the user
            isbnStmt.setString(1, idUser);
            ResultSet statusRs = isbnStmt.executeQuery();

            while (statusRs.next()) {
                String isbn = statusRs.getString("isbn");
                // Retrieve the start date for each book
                selectStmt.setString(1, idUser);
                selectStmt.setString(2, isbn);

                ResultSet selectRs = selectStmt.executeQuery();

                while (selectRs.next()) {
                    LocalDate startDate = selectRs.getDate("start_date").toLocalDate();
                    // Calculate the duration of the borrow
                    long daysDifference = ChronoUnit.DAYS.between(startDate, currentDate);

                    // Update the duration in the database
                    updateStmt.setLong(1, daysDifference);
                    updateStmt.setString(2, idUser);
                    updateStmt.setString(3, isbn);

                    int rowsAffected = updateStmt.executeUpdate();

                    // Check if the update was successful
                    if (rowsAffected > 0) {
                        System.out.println("Update successful!");
                    } else {
                        System.out.println("Update failed!");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Marks a borrowed book as returned in the database.
     *
     * @param isbn   the ISBN of the book being returned
     * @param idUser the ID of the user returning the book
     */
    public static void returnBorrow(String isbn, String idUser) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs = null;

        try {
            // Database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // SQL statement to update borrow status
            String sql1 = "UPDATE Borrow SET status = ? WHERE isbn = ? AND idUser = ?";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, 1);
            pstmt1.setString(2, isbn);
            pstmt1.setString(3, idUser);
            pstmt1.executeUpdate();

            // SQL statement to retrieve user's borrow count
            String sql2 = "SELECT number_borrow FROM user WHERE mail = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, idUser);
            rs = pstmt2.executeQuery();

            if (rs.next()) {
                int numberBorrow = rs.getInt("number_borrow");
                if (numberBorrow > 0) {
                    // SQL statement to update user's borrow count
                    String sql3 = "UPDATE user SET number_borrow = ? WHERE mail = ?";
                    pstmt3 = conn.prepareStatement(sql3);
                    pstmt3.setInt(1, numberBorrow - 1);
                    pstmt3.setString(2, idUser);
                    pstmt3.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
                if (pstmt3 != null) {
                    pstmt3.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Adds a new borrow entry and updates user's borrow count.
     *
     * @param isbn   the ISBN of the book being borrowed
     * @param idUser the ID of the user borrowing the book
     * @return true if borrow was successfully added, false otherwise
     */
    public static boolean addBorrow(String isbn, String idUser) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Retrieve user's borrow count
            String getUserBorrowCountSql = "SELECT number_borrow FROM user WHERE mail = ?";
            pstmt = conn.prepareStatement(getUserBorrowCountSql);
            pstmt.setString(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            int numberBorrow = 0;

            if (rs.next()) {
                numberBorrow = rs.getInt("number_borrow");
                System.out.println("Nombre d'emprunts actuels: " + numberBorrow);
            } else {
                System.out.println("Utilisateur non trouvé.");
            }

            // Check if the user can borrow more books
            if (numberBorrow >= 5) {
                System.out.println("L'utilisateur a atteint le nombre maximal d'emprunts.");
                return false;
            } else {
                // Add a new borrow entry
                registerBorrow(isbn, idUser);

                // Update the user's borrow count
                String updateUserBorrowCountSql = "UPDATE user SET number_borrow = number_borrow + 1 WHERE mail = ?";
                pstmt = conn.prepareStatement(updateUserBorrowCountSql);
                pstmt.setString(1, idUser);

                int updateRows = pstmt.executeUpdate();
                if (updateRows > 0) {
                    return true;
                } else {
                    System.out.println("Échec de la mise à jour du nombre d'emprunts.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Retrieves the borrowing history of a user.
     *
     * @param idUser the ID of the user
     * @return a list of Borrow objects representing the borrowing history
     */
    public static List<Borrow> historyBorrow(String idUser) {
        // List to store borrowing history
        List<Borrow> listOfBorrows = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // SQL statement to retrieve borrow details
            String statusSql = "SELECT idBorrow,isbn,duration,start_date,end_date,status FROM borrow WHERE idUser=?";
            pstmt = conn.prepareStatement(statusSql);
            // Set parameter values
            pstmt.setString(1, idUser);

            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                int idBorrow = r.getInt("idBorrow");
                String isbn = r.getString("isbn");
                int duration = r.getInt("duration");
                Date start_date = r.getDate("start_date");
                Date end_date = r.getDate("end_date");
                int status = r.getInt("status");

                // Create a Borrow object and add it to the list
                Borrow borrow = new Borrow(idBorrow, isbn, idUser, duration, start_date, end_date, status);
                listOfBorrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOfBorrows;
    }
}
