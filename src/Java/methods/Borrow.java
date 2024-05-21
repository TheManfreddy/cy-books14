package methods;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Borrow implements Serializable {
    private int idBorrow ;
    private String isbn ;
    private String idUser ;
    private int duration ;
    private Date start_date ;
    private Date end_date ;

    public Borrow(int idBorrow, String isbn, String idUser, int duration,Date start_date,Date end_date)  {
        this.idBorrow = idBorrow;
        this.isbn = isbn;
        this.idUser = idUser;
        this.duration = duration;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getIdBorrow() {
        return idBorrow;
    }
    public void setIdBorrow(int idBorrow) {
        this.idBorrow = idBorrow;
    }

    public String getIsbn() { return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {return end_date;}
    public void setEnd_date(Date stock) {this.end_date = end_date; }



    @Override
    public String toString() {
        return "methods.Borrow{" +
                "idBorrow='" + idBorrow + '\'' +
                ", isbn='" + isbn + '\'' +
                ", idUser='" + idUser + '\'' +
                "duration='" + duration + '\'' +
                "start_date='" + start_date + '\'' +
                "end_date='" + end_date + '\'' +
                '}';
    }

    public static void registerBorrow(String isbn, String idUser){


        LocalDate start_date = LocalDate.now();

        LocalDate end_date = start_date.plusDays(30);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Requête INSERT INTO avec un PreparedStatement
            String sql = "INSERT INTO Borrow (isbn,idUser,duration,start_date,end_date,status) VALUES (?, ?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);

            // Attribution des valeurs aux paramètres
            pstmt.setString(1, isbn);
            pstmt.setString(2, idUser);
            pstmt.setInt(3, 0);
            pstmt.setDate(4, java.sql.Date.valueOf(start_date));
            pstmt.setDate(5, java.sql.Date.valueOf(end_date));
            pstmt.setInt(6, 0); //

            // Exécution de la requête d'insertion
            int rowsAffected = pstmt.executeUpdate();

            // Vérification du succès de l'insertion
            if (rowsAffected > 0) {
                System.out.println("Insertion réussie !");
            } else {
                System.out.println("Échec de l'insertion !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
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

    public static void updateBorrow(String idUser) {
        LocalDate dateActuelle = LocalDate.now();

        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";
        String password = "";

        String statusSql = "SELECT isbn FROM Borrow WHERE idUser=?";
        String selectSql = "SELECT start_date FROM Borrow WHERE idUser=? AND status=0 AND isbn=?";
        String updateSql = "UPDATE Borrow SET duration = ? WHERE idUser=? AND status=0 AND isbn=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement isbnStmt = conn.prepareStatement(statusSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Vérifier le statut de l'utilisateur
            isbnStmt.setString(1, idUser);
            ResultSet statusRs = isbnStmt.executeQuery();

            while (statusRs.next()) {
                    String isbn = statusRs.getString("isbn");
                    selectStmt.setString(1, idUser);
                    selectStmt.setString(2, isbn);

                    ResultSet selectRs = selectStmt.executeQuery();

                    while (selectRs.next()) {
                        LocalDate startDate = selectRs.getDate("start_date").toLocalDate();
                        System.out.println("Start Date: " + startDate);
                        long differenceJours = ChronoUnit.DAYS.between(startDate, dateActuelle);
                        System.out.println("Difference in Days: " + differenceJours);

                        updateStmt.setLong(1, differenceJours);
                        updateStmt.setString(2, idUser);
                        updateStmt.setString(3, isbn);

                        int rowsAffected = updateStmt.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Mise à jour réussie!");
                        } else {
                            System.out.println("Échec de la mise à jour!");
                        }
                    }
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // Test the method with a sample user ID
        updateBorrow("sampleUserId");
    }



    public static void returnBorrow(String isbn, String idUser){

        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Requête permettant de mettre à jour le statut de l'emprunt
            String sql1 = "UPDATE Borrow SET status = ? WHERE isbn = ? AND idUser = ?";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, 1);
            pstmt1.setString(2, isbn);
            pstmt1.setString(3, idUser);
            pstmt1.executeUpdate();

            // Requête permettant de récupérer le nombre d'emprunts de l'utilisateur
            String sql2 = "SELECT number_borrow FROM user WHERE mail = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, idUser);
            rs = pstmt2.executeQuery();

            if (rs.next()) {
                int numberBorrow = rs.getInt("number_borrow");
                if(numberBorrow>0) {
                    // Requête permettant de mettre à jour le nombre d'emprunts de l'utilisateur
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
            // Fermeture des ressources
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
}
