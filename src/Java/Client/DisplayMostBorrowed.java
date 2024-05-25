package Client;

import Server.Manager.BookManager;
import Server.Models.Book;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The DisplayMostBorrowed class represents the user interface for displaying detailed information about the most borrowed book.
 */
public class DisplayMostBorrowed extends VBox {
    private Scene scene;

    /**
     * Constructor for DisplayMostBorrowed.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     * @param isbn         the ISBN of the book to display
     */
    public DisplayMostBorrowed(Stage primaryStage, double width, double height, String isbn) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a return button to go back to the list of most borrowed books
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");
        returnButton.setOnAction(e -> {
            MostBorrowed mostborrowed = new MostBorrowed(primaryStage, width, height);
            primaryStage.setScene(mostborrowed.getMostBorrowedScene());
        });

        // Fetch book details using the BookManager
        Book book = BookManager.displayBook(isbn);

        // Create a Label for the book title
        Label labelTitle = new Label(book.getTitle());
        labelTitle.getStyleClass().add("title");

        // Create a container for the title
        HBox titleBox = new HBox(350);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton, labelTitle);

        // Create labels and containers for book details
        Label labelTitleV = new Label("Titre : ");
        labelTitleV.getStyleClass().add("label");
        Label labelTitleValue = new Label(book.getTitle());
        labelTitleValue.getStyleClass().add("label");
        HBox titleBoxValue = new HBox(labelTitleV, labelTitleValue);

        Label labelLanguageV = new Label("Langue : ");
        labelLanguageV.getStyleClass().add("label");
        Label labelLanguageValue = new Label(book.getLanguage());
        labelLanguageValue.getStyleClass().add("label");
        HBox languageBox = new HBox(labelLanguageV, labelLanguageValue);

        Label labelAuthorV = new Label("Auteur : ");
        labelAuthorV.getStyleClass().add("label");
        Label labelAuthorValue = new Label(book.getAuthor());
        labelAuthorValue.getStyleClass().add("label");
        HBox authorBox = new HBox(labelAuthorV, labelAuthorValue);

        Label labelEditionV = new Label("Edition : ");
        labelEditionV.getStyleClass().add("label");
        Label labelEditionValue = new Label(book.getEditor());
        labelEditionValue.getStyleClass().add("label");
        HBox editionBox = new HBox(labelEditionV, labelEditionValue);

        Label labelParutionDateV = new Label("Date de parution : ");
        labelParutionDateV.getStyleClass().add("label");
        Label labelParutionDateValue = new Label(book.getRelease_year());
        labelParutionDateValue.getStyleClass().add("label");
        HBox parutionDateBox = new HBox(labelParutionDateV, labelParutionDateValue);

        // Create a "Borrow" button
        Button borrowButton = new Button("Emprunter");
        borrowButton.getStyleClass().add("button");
        borrowButton.setOnAction(e -> {
            BorrowBook borrowBook = new BorrowBook(primaryStage, width, height, isbn, book.getTitle());
            primaryStage.setScene(borrowBook.getBorrowBookScene());
        });

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(titleBoxValue, authorBox, editionBox, parutionDateBox, languageBox, borrowButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);
    }

    /**
     * Gets the scene for displaying the most borrowed book.
     *
     * @return the scene for displaying the most borrowed book
     */
    public Scene getDisplayMostBorrowedScene() {
        return scene;
    }
}
