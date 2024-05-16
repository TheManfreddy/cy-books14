import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Crée un Label pour le titre
        Label titleLabel = new Label("CYBOOKS");
        titleLabel.getStyleClass().add("title");

        // Crée un conteneur pour le titre et le centre
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Ajoute du padding autour du titre
        root.setTop(titleBox);

        // Crée un Label pour l'identifiant
        Label labelIdentifiant = new Label("Identifiant :");
        labelIdentifiant.getStyleClass().add("label");

        // Crée un champ de texte pour l'identifiant
        TextField textFieldIdentifiant = new TextField();
        textFieldIdentifiant.setPromptText("Identifiant");
        textFieldIdentifiant.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour l'identifiant et son champ de texte
        VBox identifiantBox = new VBox(5, labelIdentifiant, textFieldIdentifiant);
        identifiantBox.setAlignment(Pos.CENTER_LEFT);

        // Crée un Label pour le mot de passe
        Label labelPassword = new Label("Mot de passe :");
        labelPassword.getStyleClass().add("label");

        // Crée un champ de texte pour le mot de passe
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.getStyleClass().add("password-field");

        // Crée un conteneur VBox pour le mot de passe et son champ de texte
        VBox passwordBox = new VBox(5, labelPassword, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);

        // Crée un bouton pour se connecter
        Button userButton = new Button("Se connecter");
        userButton.getStyleClass().add("button");

        // Crée un conteneur VBox et y ajoute les composants
        VBox vbox = new VBox(15); // 15 est l'espacement entre les éléments
        vbox.getChildren().addAll(identifiantBox, passwordBox, userButton);
        vbox.getStyleClass().add("container");
        vbox.setAlignment(Pos.CENTER);

        // Place le VBox contenant le champ de texte et le bouton au centre du BorderPane
        root.setCenter(vbox);

        // Configure l'action du bouton
        userButton.setOnAction(event -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
                Statement stmt = conn.createStatement();

                ResultSet usersResultSet = stmt.executeQuery("SELECT * FROM User");
                VBox userInfoBox = new VBox(10);
                while (usersResultSet.next()) {
                    // Création des labels pour afficher les informations des utilisateurs
                    String userInfo = "Name: " + usersResultSet.getString("name") +
                            ", First Name: " + usersResultSet.getString("first_name") +
                            ", Address: " + usersResultSet.getString("adress") +
                            ", Number: " + usersResultSet.getInt("number") +
                            ", Number Borrow: " + usersResultSet.getInt("number_borrow");
                    Label userInfoLabel = new Label(userInfo);
                    userInfoLabel.getStyleClass().add("label");
                    userInfoBox.getChildren().add(userInfoLabel);
                }
                root.setCenter(userInfoBox);

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Crée et configure la scène
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test Bibli");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

