package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import methods.System1;

import java.sql.*;
import java.util.List;

public class UserProfile extends VBox {
    private Scene scene;

    public UserProfile(Stage primaryStage,double width, double height,String mail) throws SQLException {
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

        // Création du label pour afficher les informations des utilisateurs
        List<List<String>> userInformation = System1.displayUser(mail);
        mail = userInformation.get(0).get(0);
        String name = userInformation.get(0).get(1);
        String firstName = userInformation.get(0).get(2);
        String birthDate= userInformation.get(0).get(3);
        String address = userInformation.get(0).get(4);
        String phoneNumber = userInformation.get(0).get(5);
        String numberBorrow = userInformation.get(0).get(6);

        Label userMailLabel = new Label(mail);
        userMailLabel.getStyleClass().add("label");

        Label userNameLabel = new Label(name);
        userNameLabel.getStyleClass().add("label");

        Label userFirstNameLabel = new Label(firstName);
        userFirstNameLabel.getStyleClass().add("label");

        Label userBirthDateLabel = new Label(birthDate);
        userBirthDateLabel.getStyleClass().add("label");

        Label userAddressLabel = new Label(address);
        userAddressLabel.getStyleClass().add("label");

        Label userPhoneNumberLabel = new Label(phoneNumber);
        userPhoneNumberLabel.getStyleClass().add("label");

        Label userNumberBorrowLabel = new Label(numberBorrow);
        userNumberBorrowLabel.getStyleClass().add("label");

        VBox userInformationBox = new VBox();
        userInformationBox.getChildren().addAll(userNameLabel,userFirstNameLabel,userMailLabel,userBirthDateLabel,userAddressLabel,userPhoneNumberLabel,userNumberBorrowLabel);
        root.setLeft(userInformationBox);
    }

    public Scene getUserProfileScene() {
        return scene;
    }
}
