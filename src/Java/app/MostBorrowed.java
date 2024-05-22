package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import methods.APIBNF;
import methods.Librarian;
import methods.System1;
import java.util.List;

public class MostBorrowed extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 10; // Nombre d'éléments par page

    public MostBorrowed(Stage primaryStage, double width, double height) {

        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);

        // Crée un Label pour le titre
        Label titleLabel = new Label("Les livres les plus empruntés durant les 30 derniers jours");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Crée un conteneur pour le titre et le centre
        HBox titleBox = new HBox(titleLabel, returnButton);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Ajoute du padding autour du titre
        root.setTop(titleBox);
        root.setLeft(returnButton);

        // Crée un Label pour la recherche
        Label labelSearch = new Label("Recherche :");
        labelSearch.getStyleClass().add("label");

        // Crée un champ de texte pour rechercher un livre
        TextField textFieldSearch = new TextField();
        textFieldSearch.setPromptText("Rechercher un livre");
        textFieldSearch.getStyleClass().add("text-field");

        // Crée un bouton pour lancer la recherche
        Button searchButton = new Button("\uD83D\uDD0E");
        searchButton.getStyleClass().add("button");

        // Crée une ComboBox pour sélectionner la langue des livres
        ComboBox<String> langageComboBox = new ComboBox<>();
        langageComboBox.getItems().addAll("FR", "EN", "CHOISIR LANGUE");
        langageComboBox.setValue("CHOISIR LANGUE"); // Valeur par défaut

        // Ajoutez un ChangeListener pour détecter les changements de sélection
        langageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            onLanguageSelected(newValue);  // Appelez la méthode lorsque la sélection change
        });

        // Crée un bouton pour voir les livres les plus empruntés
        Button mostborrowedButton = new Button("Les plus empruntés");
        mostborrowedButton.getStyleClass().add("button");

        // Crée un conteneur VBox pour l'identifiant et son champ de texte
        HBox searchBox = new HBox(15, labelSearch, textFieldSearch, searchButton, langageComboBox);
        searchBox.setAlignment(Pos.TOP_CENTER);

        // Crée un conteneur HBox et y ajoute les composants
        HBox hBox = new HBox(50); // 50 est l'espacement entre les éléments
        hBox.getChildren().addAll(mostborrowedButton);
        hBox.getStyleClass().add("container");
        hBox.setAlignment(Pos.CENTER);

        // Crée un VBox pour contenir le label, la barre de recherche et les boutons
        VBox topBox = new VBox(15, titleBox, searchBox, hBox);
        topBox.setAlignment(Pos.CENTER);

        // Place le VBox topBox en haut du BorderPane
        root.setTop(topBox);

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            LibraryPage librarypage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(librarypage.getLibraryPageScene());
        });

        mostborrowedButton.setOnAction(e -> {
            LibraryPage librarypage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(librarypage.getLibraryPageScene());
        });


            String text = textFieldSearch.getText();
            List<List<String>> listBook = System1.mostBorrowedBooks() ;

            if (listBook != null && !listBook.isEmpty()) {
                // Convertir la liste en ObservableList pour qu'elle soit compatible avec ListView
                ObservableList<String> items = FXCollections.observableArrayList();
                for (List<String> book : listBook) {
                    items.add(String.join(", ", book)); // Convertir chaque liste de détails en une seule chaîne
                }

                // Créer un Pagination pour gérer les pages
                Pagination pagination = new Pagination((int) Math.ceil((double) items.size() / ITEMS_PER_PAGE), 0);
                pagination.setPageFactory(pageIndex -> createPage(pageIndex, items, listBook));

                // Ajouter le Pagination au centre du BorderPane
                root.setCenter(pagination);
            } else {
                // Gérer le cas où la recherche ne retourne aucun résultat
                root.setCenter(new Label("Aucun résultat trouvé."));
            }


    }

    public Scene getMostBorrowedScene() {
        return scene;
    }

    // Méthode appelée lorsque la langue est sélectionnée
    private void onLanguageSelected(String language) {
        if (language.equals("FR")) {
            String FR = "Victor Hugo";
            String queryFR = "bib.author all " + "\"" + FR + "\"";
            List<List<String>> listBooks = APIBNF.retrieveBookList(queryFR);
            System.out.println("Liste : " + listBooks);
            System.out.println("FR : " + queryFR);
        }
        if (language.equals("EN")) {
            // Ajoutez votre logique ici pour la langue EN
        }
    }

    private VBox createPage(int pageIndex, ObservableList<String> items, List<List<String>> listBook) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, items.size());
        ObservableList<String> subList = FXCollections.observableArrayList(items.subList(fromIndex, toIndex));

        ListView<String> listView = new ListView<>(subList);
        listView.setPrefHeight(subList.size() * 24 + 2); // 24 est la hauteur approximative d'une cellule

        // Ajouter un écouteur d'événements pour chaque élément de la liste
        listView.setOnMouseClicked(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int actualIndex = fromIndex + selectedIndex; // Correction ici pour obtenir l'index réel
                if (actualIndex < listBook.size()) {
                    List<String> selectedBook = listBook.get(actualIndex);
                    String isbn = selectedBook.get(0); // Récupérer l'ISBN
                    System.out.println("Selected ISBN: " + isbn); // Log pour déboguer
                    try {
                        DisplayMostBorrowed mostborrowed = new  DisplayMostBorrowed((Stage) listView.getScene().getWindow(), scene.getWidth(), scene.getHeight(), isbn);
                        ((Stage) listView.getScene().getWindow()).setScene(mostborrowed.getDisplayMostBorrowedScene());
                    } catch (Exception e) {
                        System.err.println("Failed to display book: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Index out of bounds: " + actualIndex + " for list size: " + listBook.size());
                }
            } else {
                System.err.println("Invalid selection index: " + selectedIndex);
            }
        });



        // Créer une légende
        HBox legend = new HBox(10, new Label("ISBN"), new Label("Titre"),new Label("Langue") ,new Label("Auteur"), new Label("Éditeur"), new Label("Année"), new Label("Nombre de fois emprunté"));
        legend.setStyle("-fx-padding: 5; -fx-font-weight: bold;");

        VBox vbox = new VBox(legend, listView);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

}