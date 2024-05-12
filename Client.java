package Cy_book.test;

import java.io.*;
import java.net.*;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            // Connexion au serveur
            Socket socket = new Socket("localhost", 443);
            System.out.println("Connecté au serveur.");

            // Lecture des utilisateurs envoyés par le serveur
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<User> users = (List<User>) in.readObject();
            System.out.println("Utilisateurs reçus :");
            for (User user : users) {
                System.out.println(user);
            }

            // Fermeture des ressources
            in.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
