package Client;

import Server.Manager.UserManager;
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * The ModifyInformation class represents the user interface for modifying user information.
 */
public class ModifyInformation {

    private Scene scene;
    private TextField textFieldName;
    private TextField textFieldFirstName;
    private TextField textFieldBirthDate;
    private TextField textFieldMail;
    private TextField textFieldNumber;
    private TextField textFieldAddress;

    /**
     * Constructor for the ModifyInformation class.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     * @param mail         the user's email
     * @param name         the user's name
     * @param firstName    the user's first name
     * @param birthDate    the user's birth date
     * @param address      the user's address
     * @param phoneNumber  the user's phone number
     */
    public ModifyInformation(Stage primaryStage, double width, double height, String mail, String name, String firstName, String birthDate, String address, String phoneNumber) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a return button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Configure the button to open the user page
        returnButton.setOnAction(e -> {
            UserProfile userProfile = null;
            try {
                userProfile = new UserProfile(primaryStage, width, height, mail);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.setScene(userProfile.getUserProfileScene());
        });

        // Create a Label for the title
        Label titleLabel = new Label("Modifications des informations");
        titleLabel.getStyleClass().add("title");

        // Create a container for the title
        HBox titleBox = new HBox(350); // spacing between elements
        titleBox.setAlignment(Pos.CENTER_LEFT); // align to the left
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox); // set the title box at the top of the BorderPane
        titleBox.getChildren().addAll(returnButton, titleLabel); // add the button and label to the title box

        // Create a Label for the name
        Label labelName = new Label("Nom:");
        labelName.getStyleClass().add("label");

        // Create a text field for the name
        textFieldName = new TextField(name);
        textFieldName.setPromptText("Nom");
        textFieldName.getStyleClass().add("text-field");

        // Create an HBox for the name and its text field
        HBox nameBox = new HBox(30, labelName, textFieldName);

        // Create a Label for the first name
        Label labelFirstName = new Label("Prénom:");
        labelFirstName.getStyleClass().add("label");

        // Create a text field for the first name
        textFieldFirstName = new TextField(firstName);
        textFieldFirstName.setPromptText("Prénom");
        textFieldFirstName.getStyleClass().add("text-field");

        // Create an HBox for the first name and its text field
        HBox firstNameBox = new HBox(30, labelFirstName, textFieldFirstName);

        // Create a Label for the birth date
        Label labelBirthDate = new Label("Date de naissance:");
        labelBirthDate.getStyleClass().add("label");

        // Create a text field for the birth date
        textFieldBirthDate = new TextField(birthDate);
        textFieldBirthDate.setPromptText("Date de naissance");
        textFieldBirthDate.getStyleClass().add("text-field");

        // Create an HBox for the birth date and its text field
        HBox birthDateBox = new HBox(30, labelBirthDate, textFieldBirthDate);

        // Create a Label for the email
        Label labelMail = new Label("Mail:");
        labelMail.getStyleClass().add("label");

        // Create a text field for the email
        textFieldMail = new TextField(mail);
        textFieldMail.setPromptText("Mail");
        textFieldMail.getStyleClass().add("text-field");

        // Create an HBox for the email and its text field
        HBox mailBox = new HBox(30, labelMail, textFieldMail);

        // Create a Label for the phone number
        Label labelNumber = new Label("Téléphone:");
        labelNumber.getStyleClass().add("label");

        // Create a text field for the phone number
        textFieldNumber = new TextField(phoneNumber);
        textFieldNumber.setPromptText("Téléphone");
        textFieldNumber.getStyleClass().add("text-field");

        // Create an HBox for the phone number and its text field
        HBox numberBox = new HBox(30, labelNumber, textFieldNumber);

        // Create a Label for the address
        Label labelAddress = new Label("Adresse:");
        labelAddress.getStyleClass().add("label");

        // Create a text field for the address
        textFieldAddress = new TextField(address);
        textFieldAddress.setPromptText("Adresse");
        textFieldAddress.getStyleClass().add("text-field");

        // Create an HBox for the address and its text field
        HBox addressBox = new HBox(30, labelAddress, textFieldAddress);

        // Create a validate button
        Button addUserButton = new Button("Valider");
        addUserButton.getStyleClass().add("button");

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(nameBox, firstNameBox, birthDateBox, mailBox, numberBox, addressBox, addUserButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);

        // Configure the button to open the user page after validation
        addUserButton.setOnAction(e -> {
            // Retrieve values from text fields
            String newName = textFieldName.getText();
            String newFirstName = textFieldFirstName.getText();
            String newBirthDate = textFieldBirthDate.getText();
            String newMail = textFieldMail.getText();
            String newPhoneNumber = textFieldNumber.getText();
            String newAddress = textFieldAddress.getText();

            // Validate new birth date format
            if (!isValidDateFormat(newBirthDate)) {
                showErrorAlert("Le format de la date de naissance est incorrect. Utilisez YYYY-MM-JJ.");
                return;
            }

            // Validate new email and phone number
            if (!isValidEmail(newMail)) {
                showErrorAlert("Le format de l'adresse mail est incorrect.");
                return;
            }
            if (!isValidPhoneNumber(newPhoneNumber)) {
                showErrorAlert("Le format du numéro de téléphone est incorrect.");
                return;
            }

            // Update the user information
            try {
                UserManager.modifyInformation(newMail, newName, newFirstName, newBirthDate, newAddress, newPhoneNumber);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // Navigate to the UsersPage after successful update
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });
    }

    /**
     * Validates the format of the birth date.
     *
     * @param dateStr the date string to validate
     * @return true if the date format is valid, false otherwise
     */
    private boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validates the format of the email.
     *
     * @param email the email string to validate
     * @return true if the email format is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Validates the format of the phone number.
     *
     * @param phoneNumber the phone number string to validate
     * @return true if the phone number format is valid, false otherwise
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    /**
     * Displays an error alert with a given message.
     *
     * @param message the error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the text from the name text field.
     *
     * @return the text from the name text field
     */
    public String getTextFieldName() {
        return textFieldName.getText();
    }

    /**
     * Gets the text from the first name text field.
     *
     * @return the text from the first name text field
     */
    public String getTextFieldFirstName() {
        return textFieldFirstName.getText();
    }

    /**
     * Gets the text from the birth date text field.
     *
     * @return the text from the birth date text field
     */
    public String getTextFieldBirthDate() {
        return textFieldBirthDate.getText();
    }

    /**
     * Gets the text from the email text field.
     *
     * @return the text from the email text field
     */
    public String getTextFieldMail() {
        return textFieldMail.getText();
    }

    /**
     * Gets the text from the phone number text field.
     *
     * @return the text from the phone number text field
     */
    public String getTextFieldNumber() {
        return textFieldNumber.getText();
    }

    /**
     * Gets the text from the address text field.
     *
     * @return the text from the address text field
     */
    public String getTextFieldAddress() {
        return textFieldAddress.getText();
    }

    /**
     * Gets the scene for modifying user information.
     *
     * @return the scene for modifying user information
     */
    public Scene getModifyInformationScene() {
        return scene;
    }
}
