package Client;

import Server.Manager.BookManager;
import Server.Models.Book;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * MostBorrowed class represents the user interface for displaying the most borrowed books.
 */
public class MostBorrowed extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 10; // Number of items per page

    /**
     * Constructor for the MostBorrowed class.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     */
    public MostBorrowed(Stage primaryStage, double width, double height) {

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("Les livres les plus empruntés durant les 30 derniers jours");
        titleLabel.getStyleClass().add("titleMostBorrow");

        // Create a back button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(425); // spacing between elements
        titleBox.setAlignment(Pos.CENTER_LEFT); // align to the left
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox); // set the title box at the top of the BorderPane
        titleBox.getChildren().addAll(returnButton, titleLabel); // add the button and label to the title box

        // Configure the back button
        returnButton.setOnAction(e -> {
            // Navigate back to the LibraryPage
            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(libraryPage.getLibraryPageScene());
        });

        // Fetch the list of most borrowed books
        List<Book> listBook = BookManager.mostBorrowedBooks();

        if (listBook != null && !listBook.isEmpty()) {
            // Convert the list to an ObservableList to be compatible with TableView
            ObservableList<Book> items = FXCollections.observableArrayList(listBook);

            // Create a Pagination to handle the pages
            Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, items));

            // Add the Pagination to the center of the BorderPane
            root.setCenter(pagination);
        } else {
            // Handle the case where the search returns no results
            root.setCenter(new Label("Aucun résultat trouvé."));
        }
    }

    /**
     * Returns the scene for the most borrowed books.
     *
     * @return the scene
     */
    public Scene getMostBorrowedScene() {
        return scene;
    }

    /**
     * Creates a page of TableView for pagination.
     *
     * @param pageIndex the index of the page
     * @param items     the list of books to display
     * @return a VBox containing the TableView
     */
    private VBox createPage(int pageIndex, ObservableList<Book> items) {
        // Calculate the indexes for the current page
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, items.size());
        ObservableList<Book> subList = FXCollections.observableArrayList(items.subList(fromIndex, toIndex));

        // Create a TableView with the sublist of books
        TableView<Book> tableView = new TableView<>(subList);

        // Create and configure the columns
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        isbnColumn.setPrefWidth(150); // Preferred width
        isbnColumn.setMinWidth(150);  // Minimum width
        isbnColumn.setMaxWidth(150);  // Maximum width

        TableColumn<Book, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        titleColumn.setPrefWidth(150); // Preferred width
        titleColumn.setMinWidth(150);  // Minimum width
        titleColumn.setMaxWidth(600);  // Maximum width

        TableColumn<Book, String> languageColumn = new TableColumn<>("Langue");
        languageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        languageColumn.setPrefWidth(100); // Preferred width
        languageColumn.setMinWidth(100);  // Minimum width
        languageColumn.setMaxWidth(100);  // Maximum width

        TableColumn<Book, String> authorColumn = new TableColumn<>("Auteur");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(150); // Preferred width
        authorColumn.setMinWidth(150);  // Minimum width
        authorColumn.setMaxWidth(600);  // Maximum width

        TableColumn<Book, String> editorColumn = new TableColumn<>("Éditeur");
        editorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditor()));
        editorColumn.setPrefWidth(200); // Preferred width
        editorColumn.setMinWidth(200);  // Minimum width
        editorColumn.setMaxWidth(200);  // Maximum width

        TableColumn<Book, String> yearColumn = new TableColumn<>("Année");
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRelease_year()));
        yearColumn.setPrefWidth(100); // Preferred width
        yearColumn.setMinWidth(100);  // Minimum width
        yearColumn.setMaxWidth(150);  // Maximum width

        TableColumn<Book, Integer> borrowCountColumn = new TableColumn<>("Nombre d'emprunts");
        borrowCountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowCount()));
        borrowCountColumn.setPrefWidth(200); // Preferred width
        borrowCountColumn.setMinWidth(200);  // Minimum width
        borrowCountColumn.setMaxWidth(200);  // Maximum width

        // Add all columns to the table view
        tableView.getColumns().addAll(isbnColumn, titleColumn, languageColumn, authorColumn, editorColumn, yearColumn, borrowCountColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add an event listener for each row in the table
        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Book selectedBook = row.getItem();
                    String isbn = selectedBook.getISBN(); // Retrieve the ISBN
                    System.out.println("Selected ISBN: " + isbn); // Log for debugging
                    try {
                        // Display detailed information about the selected book
                        DisplayMostBorrowed mostBorrowed = new DisplayMostBorrowed((Stage) tableView.getScene().getWindow(), scene.getWidth(), scene.getHeight(), isbn);
                        ((Stage) tableView.getScene().getWindow()).setScene(mostBorrowed.getDisplayMostBorrowedScene());
                    } catch (Exception e) {
                        System.err.println("Failed to display book: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        // Create a VBox to hold the TableView
        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_CENTER); // Align the VBox to the top center
        return vbox;
    }
}
