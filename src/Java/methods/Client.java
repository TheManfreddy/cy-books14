package methods;

import java.io.*;
import java.net.*;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            // Connexion au serveur
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connecté au serveur.");

            // Lecture de la liste des utilisateurs envoyée par le serveur
            ObjectInputStream inUsers = new ObjectInputStream(socket.getInputStream());
            List<User> users = (List<User>) inUsers.readObject();

            // Affichage des utilisateurs reçus
            System.out.println("Utilisateurs reçus :");
            for (User user : users) {
                System.out.println(user);
            }

            // Lecture de la liste des livres envoyée par le serveur
            ObjectInputStream inBooks = new ObjectInputStream(socket.getInputStream());
            List<Book> books = (List<Book>) inBooks.readObject();

            // Affichage des livres reçus
            System.out.println("Livres reçus :");
            for (Book book : books) {
                System.out.println(book);
            }

            // Lecture de la liste des prêts envoyée par le serveur
            ObjectInputStream inBorrows = new ObjectInputStream(socket.getInputStream());
            List<Borrow> borrows = (List<Borrow>) inBorrows.readObject();

            // Affichage des prêts reçus
            System.out.println("Liste des prêts reçus :");
            for (Borrow borrow : borrows) {
                System.out.println(borrow);
            }

            // Lecture de la liste des prêts envoyée par le serveur
            ObjectInputStream inLibrary = new ObjectInputStream(socket.getInputStream());
            List<Library> libraries = (List<Library>) inLibrary.readObject();

            // Affichage des libarry reçus
            System.out.println("Liste des librairies reçues :");
            for (Library library : libraries) {
                System.out.println(library);
            }
            // Fermeture des ressources
            inUsers.close();
            inBooks.close();
            inBorrows.close();
            inLibrary.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}