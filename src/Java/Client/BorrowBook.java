package Client;

import Server.Manager.BorrowManager;
import Server.Manager.UserManager;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * The BorrowBook class represents the user interface for borrowing a book.
 */
public class BorrowBook {
    private Scene scene;

    /**
     * Constructor for BorrowBook.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     * @param isbn         the ISBN of the book to be borrowed
     * @param title        the title of the book to be borrowed
     */
    public BorrowBook(Stage primaryStage, double width, double height, String isbn, String title) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a return button to go back to the list of books
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");
        returnButton.setOnAction(e -> {
            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(libraryPage.getLibraryPageScene());
        });

        // Create a Label for the title
        Label labelTitle = new Label("Emprunter");
        labelTitle.getStyleClass().add("title");

        // Create a container for the title
        HBox titleBox = new HBox(350);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton, labelTitle);

        // Create a Label for the book title
        Label labelTitleV = new Label("Titre : ");
        labelTitleV.getStyleClass().add("label");

        // Create a Label for the value of the book title
        Label labelTitleValue = new Label(title);
        labelTitleValue.getStyleClass().add("label");

        // Create a container for the title
        HBox titleBoxValue = new HBox(labelTitleV, labelTitleValue);

        // Create a Label for the user email
        Label labelMail = new Label("Mail de l'usager:");
        labelMail.getStyleClass().add("label");

        // Create a text field for the user email
        TextField textFieldMail = new TextField();
        textFieldMail.setPromptText("Saisir le mail de l'usager");
        textFieldMail.getStyleClass().add("text-field");

        // Create an HBox for the user email and its text field
        HBox mailBox = new HBox(5, labelMail, textFieldMail);

        // Create a validate button to borrow the book
        Button validateButton = new Button("Valider");
        validateButton.getStyleClass().add("button");
        validateButton.setOnAction(e -> {
            String mail = textFieldMail.getText();
            if (UserManager.isUserEmailExists(mail)) {
                // Call the method to register the borrow
                if (BorrowManager.addBorrow(isbn, mail)) {
                    LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
                    primaryStage.setScene(libraryPage.getLibraryPageScene());
                    showSuccessAlert("Emprunt ajouté avec succès.");
                }
                else{
                    showErrorAlert("L'utilisateur a dépassé le nombre d'emprunts autorisés.",primaryStage, width, height,isbn );
                }
            } else {
                // Redirect to the user registration page
                showErrorAlert("Usager non inscrit", primaryStage, width, height, isbn);
            }
        });

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(titleBoxValue, mailBox, validateButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);
    }

    /**
     * Displays an error alert with the given message and provides options to register a new user.
     *
     * @param message       the error message to display
     * @param primaryStage  the primary stage
     * @param width         the width of the scene
     * @param height        the height of the scene
     * @param isbn          the ISBN of the book to be borrowed
     */
    public static void showErrorAlert(String message, Stage primaryStage, double width, double height, String isbn) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Add a "Cancel" button
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(ButtonType.OK, cancelButtonType);

        // Display the dialog and wait for user interaction
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                // Change the scene on "OK" click
                BorrowRegisterUser borrowRegisterUser = new BorrowRegisterUser(primaryStage, width, height, isbn);
                primaryStage.setScene(borrowRegisterUser.getBorrowRegisterUserScene());
            } else if (result.get() == cancelButtonType) {
                // The "Cancel" button simply closes the alert
                alert.close();
            }
        }
    }

    /**
     * Displays a success alert with the given message.
     *
     * @param message the success message to display
     */
    private static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the scene for borrowing a book.
     *
     * @return the scene for borrowing a book
     */
    public Scene getBorrowBookScene() {
        return scene;
    }
}
