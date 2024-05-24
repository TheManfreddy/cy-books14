package Client;

import Server.Manager.BorrowManager;
import Server.Manager.UserManager;
import Server.Models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class UsersLate extends VBox {
    private Scene scene;
    private TextField textFieldResearchBar;
    private int currentPage = 0;
    private final int itemsPerPage = 8;
    private int totalPages;

    private Stage primaryStage;
    private double width;
    private double height;

    /**
     * @param primaryStage
     * @param width
     * @param height
     */
    public UsersLate(Stage primaryStage, double width, double height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;

        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Usagers");
        titleLabel.getStyleClass().add("title");


        // Crée un bouton retour
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(350);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton,titleLabel);

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });

        // Crée un champ de texte pour la barre de recherche
        textFieldResearchBar = new TextField();
        textFieldResearchBar.setPromptText(" \uD83D\uDD0E Rechercher un usager");
        textFieldResearchBar.getStyleClass().add("text-fieldSearch");

        // Crée un bouton recherche
        Button searchButton = new Button("Rechercher");
        searchButton.getStyleClass().add("button-UsersPage");

        // Configure le bouton recherche
        searchButton.setOnAction(e -> {
            String mail = getTextFieldResearchBar();
            try {
                UserManager.displayUser(mail);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            UserProfile userProfile = null;
            try {
                userProfile = new UserProfile(primaryStage, width, height, mail);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.setScene(userProfile.getUserProfileScene());
        });

        // Crée le bouton ajouter usager
        Button addUserButton = new Button("+");
        addUserButton.getStyleClass().add("button-UsersPage");

        // Crée le bouton afficher retardataire
        Button lateUserButton = new Button("Afficher les retardataires");
        lateUserButton.getStyleClass().add("buttonLate");

        // Configure le bouton afficher retardataire
        lateUserButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Crée un conteneur HBox pour la barre de recherche, le bouton ajouter usager et le bouton afficher retardataire
        HBox hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 20;");
        hBox.getChildren().addAll(textFieldResearchBar, searchButton, addUserButton, lateUserButton);

        // Configure le bouton usagers pour ouvrir la page usagers
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser(primaryStage, width, height);
            primaryStage.setScene(registerUser.getRegisterUserScene());
        });

        // Crée un TableView pour afficher les informations des utilisateurs
        TableView<User> tableView = new TableView<>();
        createTableColumns(tableView);



        // Ajoute un écouteur d'événements pour chaque ligne du tableau
        tableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    User user = row.getItem();
                    UserProfile userProfile = null;
                    try {
                        userProfile = new UserProfile(primaryStage, width, height, user.getMail());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    primaryStage.setScene(userProfile.getUserProfileScene());
                }
            });
            return row;
        });


        // Crée des boutons pour la pagination
        Button prevButton = new Button("<");
        prevButton.getStyleClass().add("button-UsersPage");
        Button nextButton = new Button(">");
        nextButton.getStyleClass().add("button-UsersPage");

        prevButton.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateUserList(tableView);
            }
        });

        nextButton.setOnAction(e -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                updateUserList(tableView);
            }
        });

        HBox paginationBox = new HBox(20, prevButton, nextButton);
        paginationBox.setAlignment(Pos.CENTER);
        paginationBox.setStyle("-fx-padding: 20;");

        // Initialisation de la liste d'utilisateurs
        List<User> userList = UserManager.displayUserBorrowLateList();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
        totalPages = (int) Math.ceil((double) userList.size() / itemsPerPage);
        updateUserList(tableView, userObservableList);

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll( hBox, tableView, paginationBox);
        finalBox.setAlignment(Pos.CENTER);
        root.setLeft(finalBox);
    }

    private void createTableColumns(TableView<User> tableView) {
        // Crée une colonne pour le nom
        TableColumn<User, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameColumn.setPrefWidth(200);

        TableColumn<User, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        firstNameColumn.setPrefWidth(200);

        TableColumn<User, String> mailColumn = new TableColumn<>("Mail");
        mailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMail()));
        mailColumn.setPrefWidth(230);

        TableColumn<User, String> birthDateColumn = new TableColumn<>("Date de naissance");
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirth_date()));
        birthDateColumn.setPrefWidth(220);

        TableColumn<User, String> addressColumn = new TableColumn<>("Adresse");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        addressColumn.setPrefWidth(220);

        TableColumn<User, String> phoneNumberColumn = new TableColumn<>("Téléphone");
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
        phoneNumberColumn.setPrefWidth(200);

        TableColumn<User, String> numberBorrowColumn = new TableColumn<>("Emprunts");
        numberBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber_borrow()));
        numberBorrowColumn.setPrefWidth(500);



        tableView.getColumns().addAll(nameColumn, firstNameColumn, mailColumn, birthDateColumn, addressColumn, phoneNumberColumn, numberBorrowColumn);

        //Redimensionne les colonnes pour occuper toute la largeur disponible
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    }

    private void updateUserList(TableView<User> tableView, ObservableList<User> userObservableList) {
        // Calcule l'indice de début de la sous-liste en fonction de la page actuelle et du nombre d'éléments par page.
        int fromIndex = currentPage * itemsPerPage;

        // Calcule l'indice de fin de la sous-liste, afin de ne pas dépasser la taille de la liste.
        int toIndex = Math.min(fromIndex + itemsPerPage, userObservableList.size());

        // Met à jour le TableView avec une sous-liste des utilisateurs correspondant à la page actuelle.
        tableView.setItems(FXCollections.observableArrayList(userObservableList.subList(fromIndex, toIndex)));
    }

    private void updateUserList(TableView<User> tableView) {
        // Récupère la liste complète des utilisateurs depuis le UserManager.
        List<User> userList = UserManager.displayUserList();

        // Convertit la liste des utilisateurs en un ObservableList
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);

        // Appelle la méthode précédente pour mettre à jour le TableView avec les données paginées.
        updateUserList(tableView, userObservableList);
    }




    public String getTextFieldResearchBar() {
        return textFieldResearchBar.getText();
    }


    public Scene getUsersLateScene() {
        return scene;
    }
}
