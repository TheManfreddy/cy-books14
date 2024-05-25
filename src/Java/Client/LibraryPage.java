package Client;

import Server.Data.APIBNF;
import Server.Manager.BookManager;
import Server.Models.Book;
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
import java.util.concurrent.*;
import javafx.application.Platform;

/**
 * The LibraryPage class represents the user interface for displaying and searching books in the library.
 */
public class LibraryPage extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 8; // Number of items per page
    private TextField textFieldSearch;
    private ComboBox<String> choiceComboBox;
    private List<Book> currentListBook;
    private ObservableList<Book> currentItems;

    private String criteria;

    /**
     * Constructor for LibraryPage with default search query and empty lists.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     */
    public LibraryPage(Stage primaryStage, double width, double height) {
        this(primaryStage, width, height, "", FXCollections.observableArrayList(), FXCollections.observableArrayList());
    }

    /**
     * Constructor for LibraryPage with specified search query and lists.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     * @param searchQuery  the search query
     * @param listBook     the list of books
     * @param items        the observable list of books
     */
    public LibraryPage(Stage primaryStage, double width, double height, String searchQuery, List<Book> listBook, ObservableList<Book> items) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("BIBLIOTHEQUE");
        titleLabel.getStyleClass().add("title");

        // Create a back button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(365);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton, titleLabel);

        // Create a text field for searching books
        textFieldSearch = new TextField(searchQuery);
        textFieldSearch.setPromptText("  Rechercher un livre");
        textFieldSearch.getStyleClass().add("text-fieldSearch");

        // Create a button to initiate the search
        Button searchButton = new Button("\uD83D\uDD0E");
        searchButton.getStyleClass().add("button-UsersPage");

        // Create a ComboBox to select the search criteria
        choiceComboBox = new ComboBox<>();
        choiceComboBox.getItems().addAll("PAR TITRE", "PAR AUTEUR", "PAR ISBN");
        choiceComboBox.setValue("PAR TITRE"); // Default value

        criteria = "PAR TITRE"; // Default value
        // Add a ChangeListener to detect changes in selection
        choiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            criteria = onChoiceSelected(newValue);  // Call method when selection changes
        });

        choiceComboBox.getStyleClass().add("button-Combobox");

        // Create a button to view the most borrowed books
        Button mostborrowedButton = new Button("Les plus empruntés");
        mostborrowedButton.getStyleClass().add("buttonMostBorrow");

        // Create an HBox container for search fields
        HBox searchBox = new HBox(15, textFieldSearch, searchButton, choiceComboBox);
        searchBox.setAlignment(Pos.TOP_CENTER);

        // Create an HBox and add the components
        HBox hBox = new HBox(50); // 50 is the spacing between elements
        hBox.getChildren().addAll(mostborrowedButton);
        hBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the label, search bar, and buttons
        VBox topBox = new VBox(15, titleBox, searchBox, hBox);
        topBox.setAlignment(Pos.CENTER);

        // Place the VBox topBox at the top of the BorderPane
        root.setTop(topBox);

        // Initialize the current state if available
        if (items != null && !items.isEmpty()) {
            currentListBook = listBook;
            currentItems = items;

            // Create a Pagination to handle pages
            Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, items, listBook));

            // Add the Pagination to the center of the BorderPane
            root.setCenter(pagination);
        }

        // Configure the back button
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });

        // Configure the most borrowed books button
        mostborrowedButton.setOnAction(e -> {
            MostBorrowed mostborrowed = new MostBorrowed(primaryStage, width, height);
            primaryStage.setScene(mostborrowed.getMostBorrowedScene());
        });

        // Configure the search button
        searchButton.setOnAction(e -> performSearch(primaryStage, root, textFieldSearch.getText()));
    }

    /**
     * Method called when a search criteria is selected.
     *
     * @param Choices the selected criteria
     * @return the selected criteria as a string
     */
    private String onChoiceSelected(String Choices) {
        String Choice = "";
        if (Choices.equals("PAR TITRE")) {
            Choice = "PAR TITRE";
        }
        if (Choices.equals("PAR AUTEUR")) {
            Choice = "PAR AUTEUR";
        }
        if (Choices.equals("PAR ISBN")) {
            Choice = "PAR ISBN";
        }
        return Choice;
    }

    /**
     * Method to perform the search operation.
     *
     * @param primaryStage the primary stage
     * @param root         the root BorderPane
     * @param text         the search text
     */
    private void performSearch(Stage primaryStage, BorderPane root, String text) {
        // ExecutorService to handle background tasks
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Task to perform the search
        Callable<List<Book>> searchTask = () -> {
            String query = APIBNF.searchBook(text, criteria);
            return BookManager.displayBookList(query);
        };

        // Future for the search task
        Future<List<Book>> future = executor.submit(searchTask);

        // Task to stop the search if it takes more than 30 seconds
        scheduler.schedule(() -> {
            if (!future.isDone()) {
                future.cancel(true); // Cancel the task
                Platform.runLater(() -> {
                    root.setCenter(new Label("Recherche arrêtée car elle a pris plus de 30 secondes."));
                });
            }
        }, 30, TimeUnit.SECONDS);

        // Execute the search and process the results
        executor.execute(() -> {
            try {
                List<Book> listBook = future.get(); // Get the search result

                Platform.runLater(() -> {
                    if (listBook != null && !listBook.isEmpty()) {
                        // Convert the list to an ObservableList to be compatible with TableView
                        ObservableList<Book> items = FXCollections.observableArrayList(listBook);

                        // Save the current state
                        currentListBook = listBook;
                        currentItems = items;

                        // Create a Pagination to handle pages
                        Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
                        pagination.setPageFactory(pageIndex -> createPage(pageIndex, items, listBook));

                        // Add the Pagination to the center of the BorderPane
                        root.setCenter(pagination);
                    } else {
                        // Gérer le cas où la recherche ne retourne aucun résultat
                        root.setCenter(new Label("Aucun résultat trouvé."));
                    }
                });
            } catch (CancellationException e) {
                // La tâche a été annulée
                System.out.println("La recherche a été annulée.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
                scheduler.shutdown();
            }
        });
    }

    /**
     * Gets the scene for the library page.
     *
     * @return the library page scene
     */
    public Scene getLibraryPageScene() {
        return scene;
    }

    /**
     * Creates a page of TableView for pagination.
     *
     * @param pageIndex the page index
     * @param items     the list of books to display
     * @param listBook  the list of all books
     * @return a VBox containing the TableView
     */
    private VBox createPage(int pageIndex, ObservableList<Book> items, List<Book> listBook) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, items.size());
        ObservableList<Book> subList = FXCollections.observableArrayList(items.subList(fromIndex, toIndex));

        TableView<Book> tableView = new TableView<>(subList);

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        isbnColumn.setPrefWidth(150); // Preferred width
        isbnColumn.setMinWidth(150);  // Minimum width
        isbnColumn.setMaxWidth(150);  // Maximum width

        TableColumn<Book, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        titleColumn.setPrefWidth(250); // Preferred width
        titleColumn.setMinWidth(250);  // Minimum width
        titleColumn.setMaxWidth(600);  // Maximum width

        TableColumn<Book, String> authorColumn = new TableColumn<>("Auteur");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(250); // Preferred width
        authorColumn.setMinWidth(250);  // Minimum width
        authorColumn.setMaxWidth(600);  // Maximum width

        TableColumn<Book, String> editorColumn = new TableColumn<>("Éditeur");
        editorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditor()));
        editorColumn.setPrefWidth(200); // Preferred width
        editorColumn.setMinWidth(200);  // Minimum width
        editorColumn.setMaxWidth(200);  // Maximum width

        TableColumn<Book, String> languageColumn = new TableColumn<>("Langue");
        languageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        languageColumn.setPrefWidth(100); // Preferred width
        languageColumn.setMinWidth(100);  // Minimum width
        languageColumn.setMaxWidth(100);  // Maximum width

        TableColumn<Book, String> releaseYearColumn = new TableColumn<>("Année");
        releaseYearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRelease_year()));
        releaseYearColumn.setPrefWidth(150); // Preferred width
        releaseYearColumn.setMinWidth(150);  // Minimum width
        releaseYearColumn.setMaxWidth(150);  // Maximum width

        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, editorColumn, languageColumn, releaseYearColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add an event listener for each row in the list
        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book selectedBook = row.getItem();
                    String isbn = selectedBook.getISBN(); // Retrieve the ISBN
                    System.out.println("Selected ISBN: " + isbn); // Log for debugging
                    try {
                        DisplayBook displayBook = new DisplayBook((Stage) tableView.getScene().getWindow(), scene.getWidth(), scene.getHeight(), isbn, textFieldSearch.getText(), currentListBook, currentItems);
                        ((Stage) tableView.getScene().getWindow()).setScene(displayBook.getDisplayBookScene());
                    } catch (Exception e) {
                        System.err.println("Failed to display book: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        VBox vbox = new VBox(15, tableView);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
