package app;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import methods.Borrow;
import methods.Librarian;
import methods.System1;

import java.sql.SQLException;

import static app.BorrowBook.showErrorAlert;

public class BorrowRegisterUser {

    private Scene scene;
    private TextField textFieldName;
    private TextField textFieldFirstName;
    private TextField textFieldBirthDate;
    private TextField textFieldMail;
    private TextField textFieldNumber;
    private TextField textFieldAddress;

    public BorrowRegisterUser(Stage primaryStage, double width, double height,String isbn){

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("Inscrire un nouvel usager");
        titleLabel.getStyleClass().add("title");

        // Create a container for the title and center it
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);

        //Create an return button
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Configure the button to open the user page
        returnButton.setOnAction(e -> {
            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(libraryPage.getLibraryPageScene());
        });

        // Create a Label for the name
        Label labelName = new Label("Nom :");
        labelName.getStyleClass().add("label");

        // Create a text field for the name
        textFieldName = new TextField();
        textFieldName.setPromptText("Nom");
        textFieldName.getStyleClass().add("text-field");

        // Create an HBox for the name and its text field
        HBox nameBox = new HBox(5, labelName, textFieldName);

        // Create a Label for the first name
        Label labelFirstName = new Label("Prénom :");
        labelFirstName.getStyleClass().add("label");

        // Create a text field for the first name
        textFieldFirstName = new TextField();
        textFieldFirstName.setPromptText("Prénom");
        textFieldFirstName.getStyleClass().add("text-field");

        // Create an HBox for the first name and its text field
        HBox firstNameBox = new HBox(5, labelFirstName, textFieldFirstName);

        // Create a Label for the birth date
        Label labelBirthDate = new Label("Date de naissance :");
        labelBirthDate.getStyleClass().add("label");

        // Create a text field for the birth date
        textFieldBirthDate = new TextField();
        textFieldBirthDate.setPromptText("Date de naissance");
        textFieldBirthDate.getStyleClass().add("text-field");

        // Create an HBox for the birth date and its text field
        HBox birthDateBox = new HBox(5, labelBirthDate, textFieldBirthDate);

        // Create a Label for the email
        Label labelMail = new Label("Mail :");
        labelMail.getStyleClass().add("label");

        // Create a text field for the email
        textFieldMail = new TextField();
        textFieldMail.setPromptText("Mail");
        textFieldMail.getStyleClass().add("text-field");

        // Create an HBox for the email and its text field
        HBox mailBox = new HBox(5, labelMail, textFieldMail);

        // Create a Label for the phone number
        Label labelNumber = new Label("Téléphone :");
        labelNumber.getStyleClass().add("label");

        // Create a text field for the phone number
        textFieldNumber = new TextField();
        textFieldNumber.setPromptText("Téléphone");
        textFieldNumber.getStyleClass().add("text-field");

        // Create an HBox for the phone number and its text field
        HBox numberBox = new HBox(5, labelNumber, textFieldNumber);

        // Create a Label for the address
        Label labelAddress = new Label("Adresse :");
        labelAddress.getStyleClass().add("label");

        // Create a text field for the address
        textFieldAddress = new TextField();
        textFieldAddress.setPromptText("Adresse");
        textFieldAddress.getStyleClass().add("text-field");

        // Create an HBox for the address and its text field
        HBox addressBox = new HBox(5, labelAddress, textFieldAddress);

        // Crée un bouton valider pour et revenir à la liste des livres
        Button validateButton = new Button("Valider");
        validateButton.getStyleClass().add("button");

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(nameBox, firstNameBox, birthDateBox, mailBox, numberBox, addressBox,validateButton ,returnButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);


        validateButton.setOnAction(e -> {
            // Retrieve values from text fields
            String name = getTextFieldName();
            String firstName = getTextFieldFirstName();
            String birthDate = getTextFieldBirthDate();
            String mail = getTextFieldMail();
            String phoneNumber = getTextFieldNumber();
            String address = getTextFieldAddress();

            try {
                Librarian.registerUser(mail,name, firstName, birthDate,address , phoneNumber,0 );
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if(System1.addBorrow(isbn, mail)==true) {
                LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
                primaryStage.setScene(libraryPage.getLibraryPageScene());
                showSuccessAlert("Emprunt ajouté avec succès.");
            }
            else{
                showErrorAlert("L'utilisateur a dépassé le nombre d'emprunts autorisés.");
            }
        });







    }
    static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public Scene getBorrowRegisterUserScene() {
        return scene;
    }

    public String getTextFieldName() {
        return textFieldName.getText();
    }

    public String getTextFieldFirstName() {
        return textFieldFirstName.getText();
    }

    public String getTextFieldBirthDate() {
        return textFieldBirthDate.getText();
    }

    public String getTextFieldMail() {
        return textFieldMail.getText();
    }

    public String getTextFieldNumber() {
        return textFieldNumber.getText();
    }

    public String getTextFieldAddress() {
        return textFieldAddress.getText();
    }
}
