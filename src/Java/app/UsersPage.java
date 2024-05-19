package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class UsersPage extends VBox {
    private Scene scene;

    public UsersPage(Stage primaryStage, double width, double height) {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);

        // Crée un Label pour le titre
        Label titleLabel = new Label("Usagers");
        titleLabel.getStyleClass().add("title");

        // Crée un conteneur pour le titre et le centre
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Ajoute du padding autour du titre
        root.setTop(titleBox);

        // Crée un bouton retour
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");


        // Crée un conteneur VBox pour le bouton Retour
        VBox returnBox = new VBox(15); // 15 est l'espacement entre les éléments
        returnBox.getChildren().addAll(returnButton);
        returnBox.getStyleClass().add("container");

        // Place le VBox contenant le champ de texte et le bouton au centre du BorderPane
        root.setTop(returnBox);

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage,width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });


        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
            Statement stmt = conn.createStatement()){

                ResultSet usersResultSet = stmt.executeQuery("SELECT * FROM user");
                VBox userInfoBox = new VBox(10);
                while (usersResultSet.next()) {
                    // Création des labels pour afficher les informations des utilisateurs
                    String userInfo = "Mail: " + usersResultSet.getString("mail") +
                            ",Name: " + usersResultSet.getString("name") +
                            ", First Name: " + usersResultSet.getString("first_name") +
                            ", Birth date: " + usersResultSet.getString("birth_date") +
                            ", Address: " + usersResultSet.getString("address") +
                            ", Phone Number: " + usersResultSet.getString("phone_number") +
                            ", Number Borrow: " + usersResultSet.getInt("number_borrow");
                    Label userInfoLabel = new Label(userInfo);
                    userInfoLabel.getStyleClass().add("label");

                    // Crée le bouton pour afficher l'oeil
                    Button eyeButton = new Button("Oeil");
                    eyeButton.getStyleClass().add("button");

                    userInfoBox.getChildren().addAll(userInfoLabel,eyeButton);

                    // Configure le bouton oeil pour ouvrir le profil usager
                    eyeButton.setOnAction(e -> {
                        UserProfile usersProfile = new UserProfile(primaryStage,width, height);
                        primaryStage.setScene(usersProfile.getUserProfileScene());
                    });
                }
                root.setCenter(userInfoBox);

        } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public Scene getUsersPageScene() {
        return scene;
    }
}