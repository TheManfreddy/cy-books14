package app;

import methods.Server;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

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
        // Création d'une instance de XAMPPManager
        Server manager = new Server();

        // Appel des méthodes nécessaires
        try {
            manager.startXAMPPServices(); // Démarrage d'Apache et MySQL
            manager.startPhpMyAdmin(); // Démarrage de PhpMyAdmin
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}


