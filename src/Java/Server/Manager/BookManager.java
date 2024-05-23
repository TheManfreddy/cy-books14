package Server.Manager;

import Server.Data.APIBNF;
import Server.Models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Server.Data.APIBNF.retrieveBookList;
import static Server.Data.APIBNF.retrieveBook_isbn;

public class BookManager {
    /**
     * @param search
     * @return
     */
    public static List<Book> displayBookList(String search) {
        // Appeler la méthode retrieveBookList pour obtenir la liste des livres
        List<List<String>> bookList = retrieveBookList(search);

        // Créer une liste pour stocker les informations formatées
        List<Book> formattedList = new ArrayList<>();

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
                Book book = new Book(isbn, bookList.get(1).get(i), bookList.get(2).get(i), bookList.get(3).get(i), bookList.get(4).get(i), bookList.get(5).get(i));
                formattedList.add(book);

                // Ajouter l'ISBN au Set
                seenIsbns.add(isbn);
            }
        }
        // Retourner la liste formatée
        return formattedList;

    }

    /**
     * @param isbn
     * @return
     */
    public static Book displayBook(String isbn) {
        Book book = retrieveBook_isbn(isbn);
        return(book);
    }

    /**
     * @return
     */
    public static List<Book> mostBorrowedBooks() {

        List<Book> mostBorrowedBooks = new ArrayList<>();

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
                Book book = retrieveBook_isbn(isbn);
                Book book1 = new Book(isbn, book.getTitle(), book.getAuthor(),book.getEditor(),book.getLanguage(),book.getRelease_year(),borrowCount);
                mostBorrowedBooks.add(book1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostBorrowedBooks;
    }
}
