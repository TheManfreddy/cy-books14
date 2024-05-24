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

public class BorrowBook {
    private Scene scene;


    /**
     * @param primaryStage
     * @param width
     * @param height
     * @param isbn
     * @param title
     */
    public BorrowBook(Stage primaryStage, double width, double height, String isbn,String title){

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un bouton retour pour revenir à la liste des livres
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
        titleBox.getChildren().addAll(returnButton,labelTitle);

        // Create a Label for the book title
        Label labelTitleV = new Label("Titre : ");
        labelTitleV.getStyleClass().add("label");

        // Create a Label for the value of the book title
        Label labelTitleValue = new Label(title);
        labelTitleValue.getStyleClass().add("label");

        // Create a container for the title
        HBox titleBoxValue = new HBox(labelTitleV,labelTitleValue);

        // Create a Label for the user mail
        Label labelMail = new Label("Mail de l'usager:");
        labelMail.getStyleClass().add("label");

        // Create a text field for the user mail
        TextField textFieldMail = new TextField();
        textFieldMail.setPromptText("Veuillez entrer le mail de l'usager");
        textFieldMail.getStyleClass().add("text-field");

        // Create an HBox for the user mail and its text field
        HBox mailBox = new HBox(5, labelMail, textFieldMail);

        // Crée un bouton valider pour revenir à la liste des livres
        Button validateButton = new Button("Valider");
        validateButton.getStyleClass().add("button");
        validateButton.setOnAction(e -> {
            String mail = textFieldMail.getText();
            if (UserManager.isUserEmailExists(mail)) {
                // Appelle la méthode registerBorrow
                if(BorrowManager.addBorrow(isbn, mail)==true) {
                    LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
                    primaryStage.setScene(libraryPage.getLibraryPageScene());
                    showSuccessAlert("Emprunt ajouté avec succès.");
                }
                else{
                    showErrorAlert("L'utilisateur a dépassé le nombre d'emprunts autorisés.");
                }
            }
            // Vérifiez dans le cas où le champ du mail saisi est null, créée Exception
            else {
                // Redirige vers la page RegisterUser
                showErrorAlert("Usager non inscrit");
                BorrowRegisterUser borrowRegisterUser = new BorrowRegisterUser(primaryStage, width, height,isbn);
                primaryStage.setScene(borrowRegisterUser.getBorrowRegisterUserScene());
            }


        });

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(titleBoxValue,mailBox,validateButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);



    }

    /**
     * @param message
     */
    static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * @param message
     */
    private static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * @return
     */
    public Scene getBorrowBookScene() {
        return scene;
    }


}
