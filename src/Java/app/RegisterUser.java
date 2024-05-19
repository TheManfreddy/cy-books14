package app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterUser {

    private Scene scene;

    public RegisterUser(Stage primaryStage, double width, double height){
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Inscrire un usager");
        titleLabel.getStyleClass().add("title");

        // Crée un conteneur pour le titre
        HBox titleBox = new HBox(titleLabel);
        root.setTop(titleBox);

        // Crée un Label pour le nom
        Label labelName = new Label("Nom :");
        labelName.getStyleClass().add("label");

        // Crée un champ de texte pour le nom
        TextField textFieldName= new TextField();
        textFieldName.setPromptText("Nom");
        textFieldName.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour le nom et son champ de texte
        HBox nameBox = new HBox(5, labelName, textFieldName);


        // Crée un Label pour le prénom
        Label labelFirstName = new Label("Prénom :");
        labelFirstName.getStyleClass().add("label");

        // Crée un champ de texte pour le prénom
        TextField textFieldFirstName= new TextField();
        textFieldFirstName.setPromptText("Prénom");
        textFieldFirstName.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour le prénom et son champ de texte
        HBox firstNameBox = new HBox(5, labelFirstName, textFieldFirstName);

        // Crée un Label pour la date de naissance
        Label labelBirthDate = new Label("Date de naissance :");
        labelBirthDate.getStyleClass().add("label");

        // Crée un champ de texte pour la date de naissance
        TextField textFieldBirthDate= new TextField();
        textFieldBirthDate.setPromptText("Date de naissance");
        textFieldBirthDate.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour la date de naissance et son champ de texte
        HBox birthDateBox = new HBox(5, labelBirthDate, textFieldBirthDate);

        // Crée un Label pour le mail
        Label labelMail = new Label("Mail :");
        labelMail.getStyleClass().add("label");

        // Crée un champ de texte pour le mail
        TextField textFieldMail = new TextField();
        textFieldMail.setPromptText("Mail");
        textFieldMail.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour le mail et son champ de texte
        HBox mailBox = new HBox(5, labelMail, textFieldMail);

        // Crée un Label pour le numéro de téléphone
        Label labelNumber = new Label("Téléphone :");
        labelNumber.getStyleClass().add("label");

        // Crée un champ de texte pour le numéro de téléphone
        TextField textFieldNumber= new TextField();
        textFieldNumber.setPromptText("Prénom");
        textFieldNumber.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour le numéro de téléphone et son champ de texte
        HBox numberBox = new HBox(5, labelNumber, textFieldNumber);

        // Crée un Label pour l'adresse
        Label labelAddress = new Label("Adresse :");
        labelAddress.getStyleClass().add("label");

        // Crée un champ de texte pour l'adresse
        TextField textFieldAddress= new TextField();
        textFieldAddress.setPromptText("Adresse");
        textFieldAddress.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour l'adresse et son champ de texte
        HBox addressBox = new HBox(5, labelAddress, textFieldAddress);

        // Crée un bouton ajouter
        Button addUserButton = new Button("Ajouter");
        addUserButton.getStyleClass().add("button");

        // Crée un conteneur VBox et y ajoute les composants
        VBox vbox = new VBox(15); // 15 est l'espacement entre les éléments
        vbox.getChildren().addAll(titleBox, nameBox,firstNameBox,birthDateBox,mailBox,numberBox, addressBox ,addUserButton);
        vbox.getStyleClass().add("container");


        // Place le VBox contenant le champ de texte et le bouton au centre du BorderPane
        root.setCenter(vbox);

        // Configure le bouton pour ouvrir la page utilisateur
        addUserButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage,width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });





    }

    public Scene getRegisterUserScene() {
        return scene;
    }
}




