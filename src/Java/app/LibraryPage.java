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

public class LibraryPage extends VBox {
    private Scene scene;

    public LibraryPage(Stage primaryStage, double width, double height) {

        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);

        // Crée un Label pour le titre
        Label titleLabel = new Label("BIBLIOTHEQUE");
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
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });

        searchButton.setOnAction(e -> {
            String text = textFieldSearch.getText();
            String query = "bib.title all " + "\"" + text + "\"";
            List<List<String>> listBook = System1.displayBookList(query);

            // Débogage : affiche le contenu de la liste
            for (List<String> book : listBook) {
                System.out.println(book);
            }

            // Convertir la liste en ObservableList pour qu'elle soit compatible avec ListView
            ObservableList<String> items = FXCollections.observableArrayList();
            for (List<String> book : listBook) {
                items.add(String.join(", ", book)); // Convertir chaque liste de détails en une seule chaîne
            }

            // Créer un ListView et ajouter les éléments
            ListView<String> listView = new ListView<>(items);

            // Ajouter un écouteur d'événements pour chaque élément de la liste
            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    showBookDetails(primaryStage, selectedItem);
                }
            });

            // Ajouter le ListView à un VBox
            VBox vbox = new VBox(listView);
            root.setCenter(vbox);
        });
    }

    public Scene getLibraryPageScene() {
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
            // displayFirstFiveElements(listBooks);
        }
        if (language.equals("EN")) {
            // Ajoutez votre logique ici pour la langue EN
        }
    }

    // Méthode pour afficher les détails du livre
    private void showBookDetails(Stage primaryStage, String bookDetails) {
        // Crée une nouvelle scène pour afficher les détails du livre
        BorderPane root = new BorderPane();
        Scene bookScene = new Scene(root, 400, 300);

        // Crée un label pour afficher les détails du livre
        Label bookLabel = new Label(bookDetails);
        bookLabel.setWrapText(true);

        // Crée un bouton "Emprunter"
        Button borrowButton = new Button("Emprunter");
        borrowButton.getStyleClass().add("button");

        // Crée un bouton retour pour revenir à la liste des livres
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");
        returnButton.setOnAction(e -> {
            primaryStage.setScene(scene);
        });

        // Crée un VBox pour contenir les détails du livre et le bouton "Emprunter"
        VBox vbox = new VBox(20, bookLabel, borrowButton, returnButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        root.setCenter(vbox);

        // Affiche la nouvelle scène
        primaryStage.setScene(bookScene);
    }
}
