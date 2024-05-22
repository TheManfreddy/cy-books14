package methods;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static methods.APIBNF.retrieveBookList;
import static methods.APIBNF.retrieveBook_isbn;

public class System1{

    public static boolean addBorrow(String isbn, String idUser){

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
            }

            // Vérifier si l'utilisateur peut emprunter plus de livres
            if (numberBorrow >= 5) {
                System.out.println("L'utilisateur a atteint le nombre maximal d'emprunts.");
                return(false);
            } else {
                // Ajouter un nouvel emprunt
                Borrow.registerBorrow(isbn, idUser);

                // Mettre à jour le nombre d'emprunts de l'utilisateur
                String updateUserBorrowCountSql = "UPDATE user SET number_borrow = number_borrow + 1 WHERE mail = ?";
                pstmt = conn.prepareStatement(updateUserBorrowCountSql);
                pstmt.setString(1, idUser);

                int updateRows = pstmt.executeUpdate();
                if (updateRows > 0) {
                    return(true);
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
    return(true);
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
                    list1.add("red");
                }
                if(status == 0 && duration<=30){
                    list1.add("green");
                }
                if(status == 1){
                    list1.add("gray");
                }

                list1.add(String.valueOf(status));

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
    public static List<List<String>> displayUser(String mail ) throws SQLException {

        List<List<String>> user = new ArrayList<>();
        user.add(Librarian.searchUser(mail));
        List<List<String>> listborrow = historyBorrow(mail);
        for (List<String> list : listborrow) {
            String isbn=list.get(0);
            List<List<String>> book = retrieveBook_isbn((String) list.get(0));
            list.remove(list.get(0));
            list.add(0,String.valueOf(book.get(0)));
            list.add(isbn);
            user.add(list);
        }
        return(user);
    }
    public static List<List<String>> displayUserList() {
        List<List<String>> UserList = new ArrayList<>();
        String query = "SELECT mail FROM  user";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mail = rs.getString("mail");
                    List user = Librarian.searchUser(mail);
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

    public static List<List<String>> displayBookList(String search) {
        // Appeler la méthode retrieveBookList pour obtenir la liste des livres
        List<List<String>> bookList = retrieveBookList(search);

        // Créer une liste pour stocker les informations formatées
        List<List<String>> formattedList = new ArrayList<>();

        // Utiliser un Set pour suivre les ISBN déjà ajoutés
        Set<String> seenIsbns = new HashSet<>();

        // Déterminer la longueur de chaque sous-liste
        int size = bookList.get(0).size();

        // Parcourir chaque élément dans les sous-listes
        for (int i = 0; i < size; i++) {
            // Obtenir l'ISBN du livre
            String isbn = bookList.get(0).get(i);

            // Vérifier si l'ISBN est déjà dans le Set
            if (!seenIsbns.contains(isbn)) {
                // Créer une nouvelle sous-liste pour stocker les informations d'un livre
                List<String> bookInfo = new ArrayList<>();

                // Ajouter les éléments correspondant à l'indice actuel à la sous-liste du livre
                bookInfo.add(isbn);
                bookInfo.add(bookList.get(1).get(i));
                bookInfo.add(bookList.get(2).get(i));
                bookInfo.add(bookList.get(3).get(i));
                bookInfo.add(bookList.get(4).get(i));
                bookInfo.add(bookList.get(5).get(i));

                // Ajouter la sous-liste du livre à la liste formatée
                formattedList.add(bookInfo);

                // Ajouter l'ISBN au Set
                seenIsbns.add(isbn);
            }
        }

        // Retourner la liste formatée
        return formattedList;

    }



    public static List<List<String> >displayBook(String isbn) {
        List<List<String>> book = retrieveBook_isbn(isbn);
        return(book);
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
                    List user = Librarian.searchUser(idUser);
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

    public static List<List<String>> mostBorrowedBooks() {

        List<List<String>> mostBorrowedBooks = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";
        String password = "";

        String query = "SELECT " +
                "    isbn, " +
                "    COUNT(*) AS borrow_count " +
                "FROM " +
                "    borrow " +
                "WHERE " +
                "    start_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                "GROUP BY " +
                "    isbn " +
                "ORDER BY " +
                " borrow_count DESC " +
                "LIMIT 20";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                int borrowCount = rs.getInt("borrow_count");
                List<List<String>> book = retrieveBook_isbn(isbn);
                List<String> book1 = new ArrayList<>();
                book1.add(isbn);
                book1.add(book.get(0).get(0));
                book1.add(book.get(1).get(0));
                book1.add(book.get(2).get(0));
                book1.add(book.get(3).get(0));
                book1.add(book.get(4).get(0));
                book1.add(String.valueOf(borrowCount));
                System.out.println("Nombre de fois emprunté " + book1);
                mostBorrowedBooks.add(book1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostBorrowedBooks;
    }
}

