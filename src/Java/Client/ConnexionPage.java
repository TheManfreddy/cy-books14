package Client;

import Server.Manager.LibraryManager;
import Server.Models.Library;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The ConnexionPage class represents the login page for the application.
 */
public class ConnexionPage extends VBox {
    private Scene scene;

    /**
     * Constructor for ConnexionPage.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     */
    public ConnexionPage(Stage primaryStage, double width, double height) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("CYBOOKS");
        titleLabel.getStyleClass().add("titleConnexion");

        // Create a container for the title and center it
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);

        // Create a Label for the username
        Label labelIdentifiant = new Label("Identifiant :");
        labelIdentifiant.getStyleClass().add("label");

        // Create a text field for the username
        TextField textFieldIdentifiant = new TextField();
        textFieldIdentifiant.setPromptText("Identifiant");
        textFieldIdentifiant.getStyleClass().add("text-field");

        // Create a VBox container for the username label and text field
        VBox identifiantBox = new VBox(5, labelIdentifiant, textFieldIdentifiant);
        identifiantBox.setAlignment(Pos.CENTER_LEFT);

        // Create a Label for the password
        Label labelPassword = new Label("Mot de passe :");
        labelPassword.getStyleClass().add("label");

        // Create a password field for the password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.getStyleClass().add("password-field");

        // Create a VBox container for the password label and password field
        VBox passwordBox = new VBox(5, labelPassword, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        passwordBox.setStyle("-fx-padding: 0 0 30 0;");

        // Create a button to log in
        Button loginButton = new Button("Se connecter");
        loginButton.getStyleClass().add("button");

        // Create a VBox container and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(titleBox, identifiantBox, passwordBox, loginButton);
        vbox.getStyleClass().add("container");
        vbox.setAlignment(Pos.CENTER);

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);

        // Configure the button to open the home page if the login is correct
        loginButton.setOnAction(e -> {
            String login = textFieldIdentifiant.getText();
            String password = passwordField.getText();
            Library library = new Library(login, password);
            if (LibraryManager.validateLogin(library)) {
                HomePage homePage = new HomePage(primaryStage, width, height);
                primaryStage.setScene(homePage.getHomePageScene());
            } else {
                showErrorAlert("Identifiant ou mot de passe incorrect.");
            }
        });
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message the error message to display
     */
    private static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the scene for the login page.
     *
     * @return the login page scene
     */
    public Scene getConnexionPageScene() {
        return scene;
    }
}
