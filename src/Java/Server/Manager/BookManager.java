package Server.Manager;

import Server.Models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Server.Data.APIBNF.retrieveBookList;
import static Server.Data.APIBNF.retrieveBook_isbn;

/**
 * Manages operations related to books such as displaying book lists and retrieving book details.
 */
public class BookManager {

    /**
     * Displays a list of books based on the search criteria.
     *
     * @param search the search criteria
     * @return a list of books matching the search criteria
     */
    public static List<Book> displayBookList(String search) {
        // Call the retrieveBookList method to get the list of books
        List<List<String>> bookList = retrieveBookList(search);

        // Create a list to store formatted book information
        List<Book> formattedList = new ArrayList<>();

        // Use a Set to keep track of seen ISBNs to avoid duplicates
        Set<String> seenIsbns = new HashSet<>();

        // Determine the length of each sublist
        int size = bookList.get(0).size();

        // Iterate over each element in the sublists
        for (int i = 0; i < size; i++) {
            // Get the ISBN of the book
            String isbn = bookList.get(0).get(i);

            // Check if the ISBN is not already in the Set
            if (!seenIsbns.contains(isbn)) {
                // Create a new Book object with retrieved information
                Book book = new Book(isbn, bookList.get(1).get(i), bookList.get(2).get(i), bookList.get(3).get(i), bookList.get(4).get(i), bookList.get(5).get(i));
                // Add the book to the formatted list
                formattedList.add(book);

                // Add the ISBN to the Set to avoid duplicates
                seenIsbns.add(isbn);
            }
        }
        // Return the formatted list of books
        return formattedList;
    }

    /**
     * Retrieves details of a book based on its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the book details
     */
    public static Book displayBook(String isbn) {
        // Retrieve book details using the ISBN
        return retrieveBook_isbn(isbn);
    }

    /**
     * Retrieves a list of the most borrowed books within the last 30 days.
     *
     * @return a list of the most borrowed books
     */
    public static List<Book> mostBorrowedBooks() {
        // Initialize a list to store the most borrowed books
        List<Book> mostBorrowedBooks = new ArrayList<>();

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";
        String password = "";

        // SQL query to retrieve most borrowed books within the last 30 days
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
                "    borrow_count DESC " +
                "LIMIT 8";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over the result set
            while (rs.next()) {
                // Retrieve ISBN and borrow count
                String isbn = rs.getString("isbn");
                int borrowCount = rs.getInt("borrow_count");
                // Retrieve book details using ISBN
                Book book = retrieveBook_isbn(isbn);
                // Create a new Book object with borrow count
                Book book1 = new Book(isbn, book.getTitle(), book.getAuthor(), book.getEditor(), book.getLanguage(), book.getRelease_year(), borrowCount);
                // Add the book to the list of most borrowed books
                mostBorrowedBooks.add(book1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return the list of most borrowed books
        return mostBorrowedBooks;
    }
}