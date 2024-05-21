package app;

import methods.Server;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Scanner;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Obtenir les dimensions de l'écran
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Créer une instance de ConnexionPage avec les dimensions de l'écran
        ConnexionPage connexionPage = new ConnexionPage(primaryStage, bounds.getWidth(), bounds.getHeight());

        // Obtenir la scène de connexion
        Scene connexionScene = connexionPage.getConnexionPageScene();

        // Configurer la scène principale
        primaryStage.setScene(connexionScene);
        primaryStage.setTitle("CYBook");
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Création d'une instance de Server
        Server manager = new Server();
        Scanner scanner = new Scanner(System.in);

        // Appel des méthodes nécessaires
        try {
            String xamppPath = Server.findXamppPath();
            if (xamppPath == null) {
                System.out.println("Impossible de détecter automatiquement le chemin de XAMPP.");
                System.out.println("Veuillez entrer le chemin de l'installation de XAMPP (chemin complet vers xampp_start.exe):");
                xamppPath = scanner.nextLine();
            }
            Server.XAMPP_START = xamppPath;

            manager.startXAMPPServices(); // Démarrage d'Apache et MySQL

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        launch(args);
    }
}


