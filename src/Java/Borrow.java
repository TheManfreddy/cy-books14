
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.sql.*;
import java.time.temporal.Temporal;
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
        return "Borrow{" +
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



    public static void updateBorrow(String isbn, String idUser) {

        LocalDate dateActuelle = LocalDate.now();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            String statusSql = "SELECT status FROM Borrow WHERE isbn=? AND idUser=?";
            pstmt = conn.prepareStatement(statusSql);
            // Attribution des valeurs aux paramètres
            pstmt.setString(1, isbn);
            pstmt.setString(2,idUser);

            ResultSet r = pstmt.executeQuery();
            if (r.next()) {  // Déplacer le curseur au premier enregistrement
                int status = r.getInt("status");

                if(status == 0){

                    // Requête SELECT avec un PreparedStatement
                    String sql = "SELECT start_date FROM Borrow WHERE isbn=? AND idUser=?";
                    pstmt = conn.prepareStatement(sql);

                    // Attribution des valeurs aux paramètres
                    pstmt.setString(1, isbn);
                    pstmt.setString(2,idUser);

                    ResultSet rs = pstmt.executeQuery();

                    // Vérifier si le ResultSet contient des lignes
                    if (rs.next()) {
                        // Récupération de la date de début
                        LocalDate startDate = rs.getDate("start_date").toLocalDate();
                        System.out.println("start_date: " + startDate);
                        long differenceJours = ChronoUnit.DAYS.between(startDate, dateActuelle);
                        System.out.println("Difference de jours: " + differenceJours);


                        String sql1 = "UPDATE Borrow SET duration = ? WHERE isbn = ? AND idUser=? ";
                        pstmt = conn.prepareStatement(sql1);

                        // Attribution des valeurs aux paramètres
                        pstmt.setLong(1, differenceJours);
                        pstmt.setString(2, isbn);
                        pstmt.setString(3,idUser);

                        // Exécution de la requête d'insertion
                        int rowsAffected = pstmt.executeUpdate();

                        // Vérification du succès de l'insertion
                        if (rowsAffected > 0) {
                            System.out.println("Mise à jour réussie!");
                        } else {
                            System.out.println("Échec de la mise à jour !");
                        }

                    } else {
                        System.out.println("Aucune date de début trouvée pour cet ISBN.");
                    }

                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void returnBorrow(String isbn, String idUser){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Requête INSERT INTO avec un PreparedStatement
            String sql = "UPDATE Borrow SET status = ? WHERE isbn = ? AND idUser=?";
            pstmt = conn.prepareStatement(sql);

            // Attribution des valeurs aux paramètres
            pstmt.setInt(1, 1);
            pstmt.setString(2, isbn);
            pstmt.setString(3,idUser);

            // Exécution de la requête d'insertion
            int rowsAffected = pstmt.executeUpdate();

            // Vérification du succès de l'insertion
            if (rowsAffected > 0) {
                System.out.println("Retour réussi !");
            } else {
                System.out.println("Échec du retour !");
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





}
