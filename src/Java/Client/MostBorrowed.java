package Client;

import Server.Manager.BookManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Server.Data.APIBNF;

import java.util.List;

public class MostBorrowed extends VBox {
    private Scene scene;
    private static final int ITEMS_PER_PAGE = 10; // Nombre d'éléments par page

    public MostBorrowed(Stage primaryStage, double width, double height) {

        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        /*scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());*/

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

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            LibraryPage librarypage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(librarypage.getLibraryPageScene());
        });
            List<List<String>> listBook = BookManager.mostBorrowedBooks() ;

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
