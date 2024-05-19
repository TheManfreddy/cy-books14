package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends VBox {
    private Scene scene;

    public HomePage(Stage primaryStage,double width, double height) {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);

        // Crée un Label pour le titre
        Label titleLabel = new Label("CYBOOKS");
        titleLabel.getStyleClass().add("title");

        // Crée un conteneur pour le titre et le centre
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Ajoute du padding autour du titre
        root.setTop(titleBox);


        // Crée un bouton pour passer dans la section usagers
        Button usersButton = new Button("Usagers");
        usersButton.getStyleClass().add("button");

        // Crée un bouton pour passer dans la section bibliothèque
        Button libraryButton = new Button("Bibliothèque");
        libraryButton.getStyleClass().add("button");


        // Crée un conteneur VBox et y ajoute les composants
        VBox vbox = new VBox(15); // 15 est l'espacement entre les éléments
        vbox.getChildren().addAll(usersButton, libraryButton);
        vbox.getStyleClass().add("container");
        vbox.setAlignment(Pos.CENTER);

        // Place le VBox contenant le champ de texte et le bouton au centre du BorderPane
        root.setCenter(vbox);

        // Configure le bouton usagers pour ouvrir la page usagers
        usersButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage,width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

    }


    public Scene getHomePageScene() {
        return scene;
    }
}


