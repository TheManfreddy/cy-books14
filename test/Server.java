package Cy_book.test;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {
    private String userName = "root";
    private String password = "";
    private String serverName = "localhost";
    private String dbName = "bibli";
    private String portNumber = "3307";
    private String dbms = "mysql";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Chargement du pilote JDBC MySQL
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie.");
            } else {
                System.out.println("Échec de la connexion à la base de données.");
            }
        }
        System.out.println("Connecté à la base de données");
        return conn;
    }

    public static void main(String[] args) throws IOException {
        List<Loan> loans;
        List<User> users;
        List<Book> books;
        List<Library> libraries;
        try {
            Server server = new Server();
            Connection conn = server.getConnection();

            String sqlUser = "SELECT * FROM user";
            String sqlBook = "SELECT * FROM book";
            String sqlLoan = "SELECT * FROM loan";
            String sqlLibrary = "SELECT * FROM library";

            PreparedStatement statementUser = conn.prepareStatement(sqlUser);
            PreparedStatement statementBook = conn.prepareStatement(sqlBook);
            PreparedStatement statementLoan = conn.prepareStatement(sqlLoan);
            PreparedStatement statementLibrary = conn.prepareStatement(sqlLibrary);

            ResultSet resultSetUser = statementUser.executeQuery();
            ResultSet resultSetBook = statementBook.executeQuery();
            ResultSet resultSetLoan = statementLoan.executeQuery();
            ResultSet resultSetLibrary = statementLibrary.executeQuery();


            if (resultSetUser != null) {
                System.out.println("Requête SQLUser exécutée avec succès.");
            } else {
                System.out.println("Échec de l'exécution de la requête SQL.");
            }

            if (resultSetBook != null) {
                System.out.println("Requête SQLBook exécutée avec succès.");
            } else {
                System.out.println("Échec de l'exécution de la requête SQL.");
            }

            if (resultSetLoan != null) {
                System.out.println("Requête SQLLoan exécutée avec succès.");
            } else {
                System.out.println("Échec de l'exécution de la requête SQL.");
            }

            if (resultSetLibrary != null) {
                System.out.println("Requête SQLLibrary exécutée avec succès.");
            } else {
                System.out.println("Échec de l'exécution de la requête SQL.");
            }

            users = new ArrayList<>();
            while (true) {
                assert resultSetUser != null;
                if (!resultSetUser.next()) break;

                String mail = resultSetUser.getString("mail");
                String name = resultSetUser.getString("name");
                String firstName = resultSetUser.getString("first_name");
                Date date_birth = resultSetUser.getDate("date_birth");
                int number = resultSetUser.getInt("number");
                int number_borrow = resultSetUser.getInt("number_borrow");
                String adress = resultSetUser.getString("adress");

                User user = new User(mail, name, firstName, date_birth, number, number_borrow, adress);
                users.add(user);
                System.out.println("Liste d'utilisateurs créée avec succès.");

            }

            books = new ArrayList<>();
            while (true) {
                assert resultSetBook != null;
                if (!resultSetBook.next()) break;
                int ISBN = resultSetBook.getInt("ISBN");
                String title = resultSetBook.getString("title");
                String kind = resultSetBook.getString("kind");
                String author = resultSetBook.getString("author");
                String editor = resultSetBook.getString("editor");
                String picture = resultSetBook.getString("picture");
                String language = resultSetBook.getString("language");
                int release_year = resultSetBook.getInt("release_year");
                int stock = resultSetBook.getInt("stock");

                Book book = new Book(ISBN, title, kind, author, editor, picture, language, release_year, stock);
                books.add(book);
                System.out.println("Liste de livres créée avec succès.");
            }

            loans = new ArrayList<>();
            while (true) {
                assert resultSetLoan != null;
                if (!resultSetLoan.next()) break;
                int idLoan = resultSetLoan.getInt("idLoan");
                int idBook = resultSetLoan.getInt("idBook");
                String idUser = resultSetLoan.getString("idUser");
                int duration = resultSetLoan.getInt("duration");
                Date start_date = resultSetLoan.getDate("start_date");
                Date end_date = resultSetLoan.getDate("end_date");

                Loan loan = new Loan( idLoan,idBook, idUser, duration, start_date, end_date);
                loans.add(loan);

                System.out.println("Liste de prêts créée avec succès.");
            }

            libraries = new ArrayList<>();
            while (true) {
                assert resultSetLibrary != null;
                if (!resultSetLibrary.next()) break;

                String login = resultSetLibrary.getString("login");
                String password = resultSetLibrary.getString("password");

                Library library = new Library(login, password);
                libraries.add(library);
                System.out.println("Liste de la librairies créée avec succès.");


            }statementUser.close();
            resultSetUser.close();

        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }


        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Serveur en attente de connexion...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connecté.");

        ObjectOutputStream outUser = new ObjectOutputStream(clientSocket.getOutputStream());
        outUser.writeObject(users);

        ObjectOutputStream outBook = new ObjectOutputStream(clientSocket.getOutputStream());
        outBook.writeObject(books);

        ObjectOutputStream outLoan = new ObjectOutputStream(clientSocket.getOutputStream());
        outLoan.writeObject(loans);

        ObjectOutputStream outLibrary = new ObjectOutputStream(clientSocket.getOutputStream());
        outLibrary.writeObject(libraries);

        outUser.close();
        outBook.close();
        outLoan.close();
        outLibrary.close();
        clientSocket.close();
        serverSocket.close();

    }
}




