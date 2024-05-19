package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserProfile extends VBox {
    private Scene scene;

    public UserProfile(Stage primaryStage,double width, double height) {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);

        // Crée un Label pour le titre
        Label titleLabel = new Label("Profil Usager");
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
            UsersPage usersPage = new UsersPage(primaryStage,width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });


    }

    public Scene getUserProfileScene() {
        return scene;
    }
}
