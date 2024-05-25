package Client;

import Server.Server;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Scanner;

/**
 * The MainApp class serves as the entry point for the JavaFX application.
 */
public class MainApp extends Application {

    /**
     * The start method is the main entry point for all JavaFX applications.
     * It is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage primaryStage) {
        // Get the screen dimensions
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Create an instance of ConnexionPage with the screen dimensions
        ConnexionPage connexionPage = new ConnexionPage(primaryStage, bounds.getWidth(), bounds.getHeight());

        // Get the login scene
        Scene connexionScene = connexionPage.getConnexionPageScene();

        // Configure the primary stage
        primaryStage.setScene(connexionScene);
        primaryStage.setTitle("CYBook");
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }

    /**
     * The main method is the entry point for the application. It initializes the server and starts the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create an instance of Server
        Server manager = new Server();
        Scanner scanner = new Scanner(System.in);

        // Call necessary methods
        try {
            String xamppPath = Server.findXamppPath();
            if (xamppPath == null) {
                System.out.println("Impossible de détecter automatiquement le chemin de XAMPP.");
                System.out.println("Veuillez entrer le chemin de l'installation de XAMPP (chemin complet vers xampp_start.exe):");
                xamppPath = scanner.nextLine();
            }
            Server.XAMPP_START = xamppPath;

            manager.startXAMPPServices(); // Start Apache and MySQL

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        // Launch the JavaFX application
        launch(args);
    }
}
