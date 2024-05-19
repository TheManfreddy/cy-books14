package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersPage extends VBox {
    private Scene scene;

    public UsersPage(Stage primaryStage, double width, double height) {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Usagers");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Crée un conteneur HBox pour le bouton retour et le titre
        HBox topBox = new HBox(100);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton, titleLabel);

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });


        // Crée un champ de texte pour la barre de recherche
        TextField textFieldResearchBar = new TextField();
        textFieldResearchBar.setPromptText("Rechercher un usager");
        textFieldResearchBar.getStyleClass().add("text-field");

        //Crée le bouton ajouter usager
        Button addUserButton = new Button("+");
        addUserButton.getStyleClass().add("button");

        //Crée le bouton afficher retardataire
        Button lateUserButton = new Button("Afficher les retardataires");
        lateUserButton.getStyleClass().add("button");

        // Crée un conteneur HBox pour la barre de recherche, le bouton ajouter usager et le bouton afficher retardataire
        HBox hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 20;");
        hBox.getChildren().addAll(textFieldResearchBar, addUserButton, lateUserButton);




        // Configure le bouton usagers pour ouvrir la page usagers
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser(primaryStage,width,height);
            primaryStage.setScene(registerUser.getRegisterUserScene());
        });



        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
             Statement stmt = conn.createStatement()) {

            ResultSet usersResultSet = stmt.executeQuery("SELECT * FROM user");
            VBox userInfoBox = new VBox(10);
            while (usersResultSet.next()) {
                // Création des labels pour afficher les informations des utilisateurs
                String userInfo =  usersResultSet.getString("mail")
                         + usersResultSet.getString("name") + "        "
                         + usersResultSet.getString("first_name") + "        "
                         + usersResultSet.getString("birth_date") + "        "
                         + usersResultSet.getString("address") + "        "
                         + usersResultSet.getString("phone_number") + "        "
                         + usersResultSet.getInt("number_borrow") + "        ";
                Label userInfoLabel = new Label(userInfo);
                userInfoLabel.getStyleClass().add("label");

                // Crée le bouton pour afficher l'oeil
                Button eyeButton = new Button("Oeil");
                eyeButton.getStyleClass().add("button");

                userInfoBox.getChildren().addAll(userInfoLabel, eyeButton);

                // Configure le bouton oeil pour ouvrir le profil usager
                eyeButton.setOnAction(e -> {
                    UserProfile userProfile = new UserProfile(primaryStage, width, height);
                    primaryStage.setScene(userProfile.getUserProfileScene());
                });
                VBox finalBox = new VBox(15);
                finalBox.getChildren().addAll(topBox, hBox, userInfoBox);
                finalBox.setAlignment(Pos.CENTER);
                root.setCenter(finalBox);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Scene getUsersPageScene() {
        return scene;
    }
}
