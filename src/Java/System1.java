import app.Librarian;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class System1{

    public static void addBorrow(String isbn, String idUser){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Récupérer le nombre d'emprunts de l'utilisateur
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
                return;
            }

            // Vérifier si l'utilisateur peut emprunter plus de livres
            if (numberBorrow >= 5) {
                System.out.println("L'utilisateur a atteint le nombre maximal d'emprunts.");
                return;
            } else {
                // Ajouter un nouvel emprunt
                Borrow.registerBorrow(isbn, idUser);

                // Mettre à jour le nombre d'emprunts de l'utilisateur
                String updateUserBorrowCountSql = "UPDATE user SET number_borrow = number_borrow + 1 WHERE mail = ?";
                pstmt = conn.prepareStatement(updateUserBorrowCountSql);
                pstmt.setString(1, idUser);

                int updateRows = pstmt.executeUpdate();
                if (updateRows > 0) {
                    System.out.println("Nombre d'emprunts mis à jour avec succès!");
                } else {
                    System.out.println("Échec de la mise à jour du nombre d'emprunts.");
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



    public static List<List<String>> historyBorrow(String idUser){

        List<List<String>> listOfBorrows = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            String statusSql = "SELECT isbn,duration,start_date,end_date,status FROM borrow WHERE idUser=?";
            pstmt = conn.prepareStatement(statusSql);
            // Attribution des valeurs aux paramètres
            pstmt.setString(1,idUser);

            ResultSet r = pstmt.executeQuery();
            while (r.next()) {  // Déplacer le curseur au premier enregistrement

                List<String> list1 = new ArrayList<>();

                String isbn = r.getString("isbn");
                int duration = r.getInt("duration");
                Date start_date = r.getDate("start_date");
                Date end_date = r.getDate("end_date");
                int status = r.getInt("status");

                list1.add(isbn);
                list1.add(String.valueOf(duration));
                list1.add(String.valueOf(start_date));
                list1.add(String.valueOf(end_date));



                if(status == 0 && duration>30){
                    list1.add("rouge");
                }
                if(status == 0 && duration<=30){
                    list1.add("vert");
                }
                if(status == 1){
                    list1.add("gris");
                }

                listOfBorrows.add(list1);
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
        return(listOfBorrows);
    }
    public static void displayUser(String mail ) throws SQLException {
        String query = "SELECT * FROM  user WHERE mail= ?";

        Librarian.searchUser(mail);
        List<List<String>> listborrow = historyBorrow(mail);
        for (List list : listborrow) {
            String query1 = "bib.isbn all " + "\"" + list.get(0) + "\"";
            try {
                // URL de l'API BNF avec la requête
                String encodedQuery = URLEncoder.encode(query1, "UTF-8");
                String apiUrl = "http://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery;

                // Création de l'URL
                URL url = new URL(apiUrl);

                // Ouverture de la connexion HTTP
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Configuration de la méthode de requête
                conn.setRequestMethod("GET");

                // Lecture de la réponse
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Texte extrait de l'API XML
                String extractedText = response.toString();
                List<String> titre = APIBNF.extractData(extractedText, "<mxc:datafield tag=\"200\" ind1=\"1\" ind2=\" \">", "<mxc:subfield code=\"a\">");

                System.out.println("Titre" + titre);
                System.out.println("Nombre de jours depuis l'emprunt : " + list.get(1));
                System.out.println("Date d'emprunt : " + list.get(2));
                System.out.println("Date de retour prévue : " + list.get(3));
                System.out.println("Surligner le titre en " + list.get(4)); // à faire avec javafx
                System.out.println(" ");
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws SQLException{
        //historyBorrow("albertroger@gmail.com");
        //System.out.println("liste" + historyBorrow("albertroger@gmail.com"));
        displayUser("albertroger@gmail.com");

    }
}

