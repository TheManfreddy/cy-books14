package Server.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Server.Data.APIBNF.retrieveBookList;
import static Server.Data.APIBNF.retrieveBook_isbn;

public class BookManager {
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
                mostBorrowedBooks.add(book1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostBorrowedBooks;
    }
}
