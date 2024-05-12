import Cy_book.test.User;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "User", "mot_de_passe");

            // Requête SQL pour récupérer les données des utilisateurs
            String sql = "SELECT mail, name, first_name FROM User";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Création d'une liste d'utilisateurs
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                String mail = resultSet.getString("mail");
                String name = resultSet.getString("name");
                String first_name = resultSet.getString("first_name");
                User user = new User(mail, name, first_name);
                users.add(user);
            }

            // Fermeture des ressources de la base de données
            resultSet.close();
            statement.close();
            conn.close();

            // Création du socket serveur
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Serveur en attente de connexion...");

            // Attente de connexion d'un client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté.");

            // Envoi des utilisateurs récupérés via le socket
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(users);

            // Fermeture des ressources
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
