package Client;

import Server.Manager.UserManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class RegisterUser {

    private Scene scene;
    private TextField textFieldName;
    private TextField textFieldFirstName;
    private TextField textFieldBirthDate;
    private TextField textFieldMail;
    private TextField textFieldNumber;
    private TextField textFieldAddress;

    public RegisterUser(Stage primaryStage, double width, double height) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        //Create a return button
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Configure the button to open the user page
        returnButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Create a Label for the title
        Label titleLabel = new Label("Inscrire un usager");
        titleLabel.getStyleClass().add("title");

        // Create a container for the title
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);

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

        // Create an add button
        Button addUserButton = new Button("Ajouter");
        addUserButton.getStyleClass().add("button");

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(nameBox, firstNameBox, birthDateBox, mailBox, numberBox, addressBox, addUserButton, returnButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);

        // Configure the button to open the user page
        addUserButton.setOnAction(e -> {
            // Retrieve values from text fields
            String name = getTextFieldName();
            String firstName = getTextFieldFirstName();
            String birthDateStr = getTextFieldBirthDate(); // Obtenez la chaîne de la date de naissance

            // Validate date of birth format
            if (!isValidDateFormat(birthDateStr)) {
                showErrorAlert("Le format de la date de naissance est incorrect. Utilisez YYYY-MM-JJ.");
                return;
            }

            // Convert the birthDateStr to a LocalDate object
            LocalDate birthDate = LocalDate.parse(birthDateStr);

            String mail = getTextFieldMail();
            String phoneNumber = getTextFieldNumber();
            String address = getTextFieldAddress();

            // Validate email and phone number
            if (!isValidEmail(mail)) {
                showErrorAlert("Le format de l'adresse mail est incorrect.");
                return;
            }
            if (!isValidPhoneNumber(phoneNumber)) {
                showErrorAlert("Le format du numéro de téléphone est incorrect.");
                return;
            }

            try {
                // Check if user already exists
                if (UserManager.isUserEmailExists(mail)) {
                    showErrorAlert("L'utilisateur existe déjà dans la base de données.");
                } else {
                    UserManager.registerUser(mail, name, firstName, birthDate.toString(), address, phoneNumber, 0);
                    UsersPage usersPage = new UsersPage(primaryStage, width, height);
                    primaryStage.setScene(usersPage.getUsersPageScene());
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private boolean isValidDateFormat(String dateStr) {
        // Utilisez le format spécifié pour vérifier la validité de la date
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public Scene getRegisterUserScene() {
        return scene;
    }
}




