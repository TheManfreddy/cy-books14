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
 * Classe MostBorrowed représentant l'interface utilisateur pour afficher les livres les plus empruntés
 */
public class MostBorrowed extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 10; // Nombre d'éléments par page

    /**
     * Constructeur de la classe MostBorrowed
     *
     * @param primaryStage le stage principal
     * @param width        la largeur de la scène
     * @param height       la hauteur de la scène
     */
    public MostBorrowed(Stage primaryStage, double width, double height) {

        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Les livres les plus empruntés durant les 30 derniers jours");
        titleLabel.getStyleClass().add("titleMostBorrow");


        // Crée un bouton retour
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(425);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton,titleLabel);


        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            LibraryPage librarypage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(librarypage.getLibraryPageScene());
        });

        List<Book> listBook = BookManager.mostBorrowedBooks();

        if (listBook != null && !listBook.isEmpty()) {
            // Convertir la liste en ObservableList pour qu'elle soit compatible avec TableView
            ObservableList<Book> items = FXCollections.observableArrayList(listBook);

            // Créer un Pagination pour gérer les pages
            Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, items));

            // Ajouter le Pagination au centre du BorderPane
            root.setCenter(pagination);
        } else {
            // Gérer le cas où la recherche ne retourne aucun résultat
            root.setCenter(new Label("Aucun résultat trouvé."));
        }
    }

    /**
     * Retourne la scène pour les livres les plus empruntés
     *
     * @return la scène
     */
    public Scene getMostBorrowedScene() {
        return scene;
    }

    /**
     * Crée une page de TableView pour la pagination
     *
     * @param pageIndex l'index de la page
     * @param items     la liste des livres à afficher
     * @return un VBox contenant la TableView
     */
    private VBox createPage(int pageIndex, ObservableList<Book> items) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, items.size());
        ObservableList<Book> subList = FXCollections.observableArrayList(items.subList(fromIndex, toIndex));

        TableView<Book> tableView = new TableView<>(subList);

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        isbnColumn.setPrefWidth(150); // Largeur préférée
        isbnColumn.setMinWidth(150);  // Largeur minimale
        isbnColumn.setMaxWidth(150);  // Largeur maximale

        TableColumn<Book, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        titleColumn.setPrefWidth(150); // Largeur préférée
        titleColumn.setMinWidth(150);  // Largeur minimale
        titleColumn.setMaxWidth(600);  // Largeur maximale

        TableColumn<Book, String> languageColumn = new TableColumn<>("Langue");
        languageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        languageColumn.setPrefWidth(100); // Largeur préférée
        languageColumn.setMinWidth(100);  // Largeur minimale
        languageColumn.setMaxWidth(100);  // Largeur maximale

        TableColumn<Book, String> authorColumn = new TableColumn<>("Auteur");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(150); // Largeur préférée
        authorColumn.setMinWidth(150);  // Largeur minimale
        authorColumn.setMaxWidth(600);  // Largeur maximale

        TableColumn<Book, String> editorColumn = new TableColumn<>("Éditeur");
        editorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditor()));
        editorColumn.setPrefWidth(200); // Largeur préférée
        editorColumn.setMinWidth(200);  // Largeur minimale
        editorColumn.setMaxWidth(200);  // Largeur maximale

        TableColumn<Book, String> yearColumn = new TableColumn<>("Année");
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRelease_year()));
        yearColumn.setPrefWidth(100); // Largeur préférée
        yearColumn.setMinWidth(100);  // Largeur minimale
        yearColumn.setMaxWidth(150);  // Largeur maximale

        TableColumn<Book, Integer> borrowCountColumn = new TableColumn<>("Nombre d'emprunts");
        borrowCountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBorrowCount()));
        borrowCountColumn.setPrefWidth(200); // Largeur préférée
        borrowCountColumn.setMinWidth(200);  // Largeur minimale
        borrowCountColumn.setMaxWidth(200);  // Largeur maximale

        tableView.getColumns().addAll(isbnColumn, titleColumn, languageColumn, authorColumn, editorColumn, yearColumn, borrowCountColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Ajouter un écouteur d'événements pour chaque ligne de la table
        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Book selectedBook = row.getItem();
                    String isbn = selectedBook.getISBN(); // Récupérer l'ISBN
                    System.out.println("Selected ISBN: " + isbn); // Log pour déboguer
                    try {
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

        VBox vbox = new VBox(tableView);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }
}
