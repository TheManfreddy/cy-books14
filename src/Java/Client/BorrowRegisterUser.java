package Client;

import Server.Manager.BorrowManager;
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
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The BorrowRegisterUser class represents the user interface for registering a new user and borrowing a book.
 */
public class BorrowRegisterUser {

    private Scene scene;
    private TextField textFieldName;
    private TextField textFieldFirstName;
    private TextField textFieldBirthDate;
    private TextField textFieldMail;
    private TextField textFieldNumber;
    private TextField textFieldAddress;

    /**
     * Constructor for BorrowRegisterUser.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     * @param isbn         the ISBN of the book to be borrowed
     */
    public BorrowRegisterUser(Stage primaryStage, double width, double height, String isbn) {

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("Inscrire un nouvel usager");
        titleLabel.getStyleClass().add("title");

        // Create a return button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(350);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton, titleLabel);

        // Configure the button to open the library page
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
        HBox nameBox = new HBox(30, labelName, textFieldName);

        // Create a Label for the first name
        Label labelFirstName = new Label("Prénom :");
        labelFirstName.getStyleClass().add("label");

        // Create a text field for the first name
        textFieldFirstName = new TextField();
        textFieldFirstName.setPromptText("Prénom");
        textFieldFirstName.getStyleClass().add("text-field");

        // Create an HBox for the first name and its text field
        HBox firstNameBox = new HBox(30, labelFirstName, textFieldFirstName);

        // Create a Label for the birth date
        Label labelBirthDate = new Label("Date de naissance :");
        labelBirthDate.getStyleClass().add("label");

        // Create a text field for the birth date
        textFieldBirthDate = new TextField();
        textFieldBirthDate.setPromptText("Date de naissance");
        textFieldBirthDate.getStyleClass().add("text-field");

        // Create an HBox for the birth date and its text field
        HBox birthDateBox = new HBox(30, labelBirthDate, textFieldBirthDate);

        // Create a Label for the email
        Label labelMail = new Label("Mail :");
        labelMail.getStyleClass().add("label");

        // Create a text field for the email
        textFieldMail = new TextField();
        textFieldMail.setPromptText("Mail");
        textFieldMail.getStyleClass().add("text-field");

        // Create an HBox for the email and its text field
        HBox mailBox = new HBox(30, labelMail, textFieldMail);

        // Create a Label for the phone number
        Label labelNumber = new Label("Téléphone :");
        labelNumber.getStyleClass().add("label");

        // Create a text field for the phone number
        textFieldNumber = new TextField();
        textFieldNumber.setPromptText("Téléphone");
        textFieldNumber.getStyleClass().add("text-field");

        // Create an HBox for the phone number and its text field
        HBox numberBox = new HBox(30, labelNumber, textFieldNumber);

        // Create a Label for the address
        Label labelAddress = new Label("Adresse :");
        labelAddress.getStyleClass().add("label");

        // Create a text field for the address
        textFieldAddress = new TextField();
        textFieldAddress.setPromptText("Adresse");
        textFieldAddress.getStyleClass().add("text-field");

        // Create an HBox for the address and its text field
        HBox addressBox = new HBox(30, labelAddress, textFieldAddress);

        // Crée un bouton valider pour et revenir à la liste des livres
        Button validateButton = new Button("Valider");
        validateButton.getStyleClass().add("button");

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(nameBox, firstNameBox, birthDateBox, mailBox, numberBox, addressBox, validateButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);

        // Configure the validate button to register the user and borrow the book
        validateButton.setOnAction(e -> {
            // Retrieve values from text fields
            String name = getTextFieldName();
            String firstName = getTextFieldFirstName();
            String birthDateStr = getTextFieldBirthDate();

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
                    if (!Objects.equals(name, "") && !Objects.equals(firstName, "") && !Objects.equals(address, "")) {
                        UserManager.registerUser(mail, name, firstName, birthDateStr, address, phoneNumber, 0);
                        if (BorrowManager.addBorrow(isbn, mail)) {
                            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
                            primaryStage.setScene(libraryPage.getLibraryPageScene());
                            showSuccessAlert("Emprunt ajouté avec succès.");
                        }
                        else{
                            showErrorAlert("L'utilisateur a dépassé le nombre d'emprunts autorisés.");
                        }
                    }else {
                        showErrorAlert("Veuillez remplir chaque champs.");
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message the error message to display
     */
    static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a success alert with the given message.
     *
     * @param message the success message to display
     */
    private static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
     * Gets the scene for registering a user and borrowing a book.
     *
     * @return the scene for registering a user and borrowing a book
     */
    public Scene getBorrowRegisterUserScene() {
        return scene;
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
}
