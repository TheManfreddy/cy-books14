package app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Profil Usager");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage,width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Crée un conteneur HBox pour le bouton retour et le titre
        HBox topBox = new HBox(100);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton, titleLabel);


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

        // Crée un bouton modifications des informations
        Button modifyButton = new Button("Modifications des informations");
        modifyButton.getStyleClass().add("button");

        //Configure le bouton modifier information
        String finalMail = mail;
        modifyButton.setOnAction(e -> {
            ModifyInformation modifyInformation = new ModifyInformation(primaryStage,width, height,  finalMail, name, firstName, birthDate, address, phoneNumber);
            primaryStage.setScene(modifyInformation.getModifyInformationScene());
        });


        HBox userInformationAndModifyButton = new HBox(15);
        VBox userInformationBox = new VBox();
        userInformationBox.getChildren().addAll(userNameLabel,userFirstNameLabel,userMailLabel,userBirthDateLabel,userAddressLabel,userPhoneNumberLabel,userNumberBorrowLabel);
        userInformationAndModifyButton.getChildren().addAll(userInformationBox,modifyButton);
        //Affichage historique emprunts
        userInformation.remove(0);
        HBox borrowsInformationBox = new HBox(30);
        for (List<String> borrow :userInformation){
            VBox borrowInformationBox = new VBox(15);
            String title = borrow.get(0);
            String duration = borrow.get(1);
            String startDate = borrow.get(2);
            String endDate = borrow.get(3);
            String color = borrow.get(4);

            //Création des labels pour afficher l'historique des emprunts
            Label borrowTitleLabel = new Label(title);
            if(color=="red") {
                borrowTitleLabel.getStyleClass().add("highlighted-label-red");
            }
            if(color=="green") {
                borrowTitleLabel.getStyleClass().add("highlighted-label-green");
            }
            if(color=="gray") {
                borrowTitleLabel.getStyleClass().add("highlighted-label-gray");
            }

            Label borrowDurationLabel = new Label(duration);
            borrowDurationLabel.getStyleClass().add("label");

            Label borrowStartDateLabel = new Label(startDate);
            borrowStartDateLabel.getStyleClass().add("label");

            Label borrowEndDateLabel = new Label(endDate);
            borrowEndDateLabel.getStyleClass().add("label");


            borrowInformationBox.getChildren().addAll(borrowTitleLabel,borrowDurationLabel,borrowStartDateLabel,borrowEndDateLabel);
            borrowsInformationBox.getChildren().add(borrowInformationBox);

        }
        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(topBox,userInformationAndModifyButton, borrowsInformationBox);
        finalBox.setAlignment(Pos.CENTER);
        root.setLeft(finalBox);
    }

    public Scene getUserProfileScene() {
        return scene;
    }
}
