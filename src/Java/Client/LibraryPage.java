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

public class LibraryPage extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 8; // Nombre d'éléments par page
    private TextField textFieldSearch;
    private ComboBox<String> choiceComboBox;
    private List<Book> currentListBook;
    private ObservableList<Book> currentItems;

    private String criteria;

    /**
     * @param primaryStage
     * @param width
     * @param height
     */
    public LibraryPage(Stage primaryStage, double width, double height) {
        this(primaryStage, width, height, "", FXCollections.observableArrayList(), FXCollections.observableArrayList());
    }

    /**
     * @param primaryStage
     * @param width
     * @param height
     * @param searchQuery
     * @param listBook
     * @param items
     */
    public LibraryPage(Stage primaryStage, double width, double height, String searchQuery, List<Book> listBook, ObservableList<Book> items) {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("BIBLIOTHEQUE");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(365);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton,titleLabel);


        // Crée un champ de texte pour rechercher un livre
        textFieldSearch = new TextField(searchQuery);
        textFieldSearch.setPromptText("  Rechercher un livre");
        textFieldSearch.getStyleClass().add("text-fieldSearch");

        // Crée un bouton pour lancer la recherche
        Button searchButton = new Button("\uD83D\uDD0E");
        searchButton.getStyleClass().add("button-UsersPage");

        // Crée une ComboBox pour sélectionner la langue des livres
        choiceComboBox = new ComboBox<>();

        choiceComboBox.getItems().addAll("PAR TITRE", "PAR AUTEUR", "PAR ISBN");
        choiceComboBox.setValue("PAR TITRE"); // Valeur par défaut

        criteria = "PAR TITRE"; // Valeur par défaut
        // Ajoutez un ChangeListener pour détecter les changements de sélection
        choiceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            criteria = onChoiceSelected(newValue);  // Appelez la méthode lorsque la sélection change
        });

        choiceComboBox.getStyleClass().add("button-Combobox");




        // Crée un bouton pour voir les livres les plus empruntés
        Button mostborrowedButton = new Button("Les plus empruntés");
        mostborrowedButton.getStyleClass().add("buttonMostBorrow");

        // Crée un conteneur HBox pour les champs de recherche
        HBox searchBox = new HBox(15, textFieldSearch, searchButton, choiceComboBox);
        searchBox.setAlignment(Pos.TOP_CENTER);

        // Crée un conteneur HBox et y ajoute les composants
        HBox hBox = new HBox(50); // 50 est l'espacement entre les éléments
        hBox.getChildren().addAll(mostborrowedButton);
        hBox.setAlignment(Pos.CENTER);


        // Crée un VBox pour contenir le label, la barre de recherche et les boutons
        VBox topBox = new VBox(15, titleBox, searchBox, hBox);
        topBox.setAlignment(Pos.CENTER);

        // Place le VBox topBox en haut du BorderPane
        root.setTop(topBox);

        // Initialiser l'état actuel si disponible
        if (items != null && !items.isEmpty()) {
            currentListBook = listBook;
            currentItems = items;

            // Créer un Pagination pour gérer les pages
            Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
           pagination.setPageFactory(pageIndex -> createPage(pageIndex, items, listBook));

            // Ajouter le Pagination au centre du BorderPane
            root.setCenter(pagination);
        }

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });
        // Configure le bouton les plus empruntés
        mostborrowedButton.setOnAction(e -> {
            MostBorrowed mostborrowed = new MostBorrowed(primaryStage, width, height);
            primaryStage.setScene(mostborrowed.getMostBorrowedScene());
        });
        // Configure le bouton rechercher
        searchButton.setOnAction(e -> performSearch(primaryStage, root, textFieldSearch.getText()));
    }

    // Méthode appelée lorsque la langue est sélectionnée
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
     * @param primaryStage
     * @param root
     * @param text
     */
    private void performSearch(Stage primaryStage, BorderPane root, String text) {
        // ExecutorService pour gérer les tâches en arrière-plan
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Tâche pour effectuer la recherche
        Callable<List<Book>> searchTask = () -> {
            String query = APIBNF.searchBook(text, criteria);
            return BookManager.displayBookList(query);
        };

        // Future pour la recherche
        Future<List<Book>> future = executor.submit(searchTask);

        // Tâche pour arrêter la recherche si elle prend plus de 30 secondes
        scheduler.schedule(() -> {
            if (!future.isDone()) {
                future.cancel(true); // Annuler la tâche
                Platform.runLater(() -> {
                    root.setCenter(new Label("Recherche arrêtée car elle a pris plus de 30 secondes."));
                });
            }
        }, 30, TimeUnit.SECONDS);

        // Exécuter la recherche et traiter les résultats
        executor.execute(() -> {
            try {
                List<Book> listBook = future.get(); // Obtenir le résultat de la recherche

                Platform.runLater(() -> {
                    if (listBook != null && !listBook.isEmpty()) {
                        // Convertir la liste en ObservableList pour qu'elle soit compatible avec TableView
                        ObservableList<Book> items = FXCollections.observableArrayList(listBook);

                        // Sauvegarder l'état actuel
                        currentListBook = listBook;
                        currentItems = items;

                        // Créer un Pagination pour gérer les pages
                        Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
                        pagination.setPageFactory(pageIndex -> createPage(pageIndex, items, listBook));

                        // Ajouter le Pagination au centre du BorderPane
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
     * @return
     */
    public Scene getLibraryPageScene() {
        return scene;
    }

    /**
     * @param pageIndex
     * @param items
     * @param listBook
     * @return
     */
    private VBox createPage(int pageIndex, ObservableList<Book> items, List<Book> listBook) {
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
        titleColumn.setPrefWidth(250); // Largeur préférée
        titleColumn.setMinWidth(250);  // Largeur minimale
        titleColumn.setMaxWidth(600);  // Largeur maximale

        TableColumn<Book, String> authorColumn = new TableColumn<>("Auteur");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(250); // Largeur préférée
        authorColumn.setMinWidth(250);  // Largeur minimale
        authorColumn.setMaxWidth(600);  // Largeur maximale

        TableColumn<Book, String> editorColumn = new TableColumn<>("Éditeur");
        editorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditor()));
        editorColumn.setPrefWidth(200); // Largeur préférée
        editorColumn.setMinWidth(200);  // Largeur minimale
        editorColumn.setMaxWidth(200);  // Largeur maximale

        TableColumn<Book, String> languageColumn = new TableColumn<>("Langue");
        languageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        languageColumn.setPrefWidth(100); // Largeur préférée
        languageColumn.setMinWidth(100);  // Largeur minimale
        languageColumn.setMaxWidth(100);  // Largeur maximale

        TableColumn<Book, String> releaseYearColumn = new TableColumn<>("Année");
        releaseYearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRelease_year()));
        releaseYearColumn.setPrefWidth(150); // Largeur préférée
        releaseYearColumn.setMinWidth(150);  // Largeur minimale
        releaseYearColumn.setMaxWidth(150);  // Largeur maximale




        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, editorColumn, languageColumn, releaseYearColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Ajouter un écouteur d'événements pour chaque élément de la liste

        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book selectedBook = row.getItem();
                    String isbn = selectedBook.getISBN(); // Récupérer l'ISBN
                    System.out.println("Selected ISBN: " + isbn); // Log pour déboguer
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

        VBox vbox = new VBox(15,tableView);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}

