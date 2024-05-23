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

import javafx.collections.ObservableList;
import java.util.List;

public class DisplayBook {

    private Scene scene;
    private String searchQuery;
    private List<Book> currentListBook;
    private ObservableList<Book> currentItems;

    public DisplayBook(Stage primaryStage, double width, double height, String isbn, String searchQuery, List<Book> listBook, ObservableList<Book> items) {
        this.searchQuery = searchQuery;
        this.currentListBook = listBook;
        this.currentItems = items;

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un bouton retour pour revenir à la liste des livres
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");
        returnButton.setOnAction(e -> {
            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height, searchQuery, currentListBook, currentItems);
            primaryStage.setScene(libraryPage.getLibraryPageScene());
        });

        Book bookDetails = BookManager.displayBook(isbn);

        String title = bookDetails.getTitle();
        String language = bookDetails.getLanguage();
        String author = bookDetails.getAuthor();
        String edition = bookDetails.getEditor();
        String publicationDate = bookDetails.getRelease_year();

        // Create a Label for the title
        Label labelTitle = new Label(title);
        labelTitle.getStyleClass().add("title");

        // Create a container for the title
        HBox titleBox = new HBox(labelTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");
        root.setTop(titleBox);

        // Create a Label for the book title
        Label labelTitleV = new Label("Titre : ");
        labelTitleV.getStyleClass().add("label");

        // Create a Label for the value of the book title
        Label labelTitleValue = new Label(title);
        labelTitleValue.getStyleClass().add("label");

        // Create a container for the title
        HBox titleBoxValue = new HBox(labelTitleV, labelTitleValue);

        // Create a Label for the language
        Label labelLanguageV = new Label("Langue : ");
        labelLanguageV.getStyleClass().add("label");

        // Create a Label for the value of the book language
        Label labelLanguageValue = new Label(language);
        labelLanguageValue.getStyleClass().add("label");

        // Create a container for the language
        HBox languageBox = new HBox(labelLanguageV, labelLanguageValue);

        // Create a Label for the author
        Label labelAuthorV = new Label("Auteur : ");
        labelAuthorV.getStyleClass().add("label");

        // Create a Label for the value of the book author
        Label labelAuthorValue = new Label(author);
        labelAuthorValue.getStyleClass().add("label");

        // Create a container for the author
        HBox authorBox = new HBox(labelAuthorV, labelAuthorValue);

        // Create a Label for the edition
        Label labelEditionV = new Label("Edition : ");
        labelEditionV.getStyleClass().add("label");

        // Create a Label for the value of the book edition
        Label labelEditionValue = new Label(edition);
        labelEditionValue.getStyleClass().add("label");

        // Create a container for the edition
        HBox editionBox = new HBox(labelEditionV, labelEditionValue);

        // Create a Label for the parution date
        Label labelParutionDateV = new Label("Date de parution : ");
        labelParutionDateV.getStyleClass().add("label");

        // Create a Label for the value of the book parution date
        Label labelParutionDateValue = new Label(publicationDate);
        labelParutionDateValue.getStyleClass().add("label");

        // Create a container for the parution date
        HBox parutionDateBox = new HBox(labelParutionDateV, labelParutionDateValue);

        // Crée un bouton "Emprunter"
        Button borrowButton = new Button("Emprunter");
        borrowButton.getStyleClass().add("button");
        borrowButton.setOnAction(e -> {
            BorrowBook borrowBook = new BorrowBook(primaryStage, width, height, isbn, title);
            primaryStage.setScene(borrowBook.getBorrowBookScene());
        });

        // Create a VBox and add the components
        VBox vbox = new VBox(15); // 15 is the spacing between elements
        vbox.getChildren().addAll(titleBoxValue, authorBox, editionBox, parutionDateBox, languageBox, borrowButton, returnButton);
        vbox.getStyleClass().add("container");

        // Place the VBox containing the text fields and button in the center of the BorderPane
        root.setCenter(vbox);
    }

    public Scene getDisplayBookScene() {
        return scene;
    }
}
