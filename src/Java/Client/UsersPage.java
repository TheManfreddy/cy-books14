package Client;

import Server.Manager.UserManager;
import Server.Models.User;
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

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static Server.Manager.BorrowManager.updateBorrow;

/**
 * This class represents the page where users' information is displayed and managed.
 */
public class UsersPage extends VBox {
    private Scene scene;
    private TextField textFieldResearchBar;
    private int currentPage = 0;
    private final int itemsPerPage = 8;
    private int totalPages;

    private Stage primaryStage;
    private double width;
    private double height;

    /**
     * Constructs a UsersPage object.
     *
     * @param primaryStage The primary stage of the application
     * @param width        The width of the scene
     * @param height       The height of the scene
     */
    public UsersPage(Stage primaryStage, double width, double height) {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;

        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("Usagers");
        titleLabel.getStyleClass().add("title");

        // Create a return button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Create a container for the title
        HBox titleBox = new HBox(365);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setStyle("-fx-padding: 20;");  // Add padding around the title
        root.setTop(titleBox);
        titleBox.getChildren().addAll(returnButton, titleLabel);

        // Configure the return button
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });

        // Create a text field for the search bar
        textFieldResearchBar = new TextField();
        textFieldResearchBar.setPromptText(" \uD83D\uDD0E Rechercher un usager");
        textFieldResearchBar.getStyleClass().add("text-fieldSearch");

        // Create a search button
        Button searchButton = new Button("Rechercher");
        searchButton.getStyleClass().add("button-UsersPage");

        // Configure the search button
        searchButton.setOnAction(e -> {
            String mail = getTextFieldResearchBar();

            UserProfile userProfile;
            try {
                if (!Objects.equals(mail, "")) {
                    if (!UserManager.isUserEmailExists(mail)){
                        showErrorAlert("L'usager n'existe pas ");
                    } else {
                        userProfile = new UserProfile(primaryStage, width, height, mail);
                        primaryStage.setScene(userProfile.getUserProfileScene());
                    }
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        // Create the add user button
        Button addUserButton = new Button("➕");
        addUserButton.getStyleClass().add("button-UsersPage");

        // Configure the add user button to open the register user page
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser(primaryStage, width, height);
            primaryStage.setScene(registerUser.getRegisterUserScene());
        });

        // Create the show late users button
        Button lateUserButton = new Button("Afficher les retardataires");
        lateUserButton.getStyleClass().add("button-UsersPage");

        // Configure the show late users button
        lateUserButton.setOnAction(e -> {
            UsersLate usersLate = new UsersLate(primaryStage, width, height);
            primaryStage.setScene(usersLate.getUsersLateScene());
        });

        // Create an HBox container for the search bar, add user button, and show late users button
        HBox hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 20;");
        hBox.getChildren().addAll(textFieldResearchBar, searchButton, addUserButton, lateUserButton);

        // Create a TableView to display users' information
        TableView<User> tableView = new TableView<>();
        createTableColumns(tableView);

        // Add an event listener for each row in the table
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

        // Create buttons for pagination
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

        // Initialize the list of users
        List<User> userList = UserManager.displayUserList();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
        totalPages = (int) Math.ceil((double) userList.size() / itemsPerPage);
        updateUserList(tableView, userObservableList);

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(hBox, tableView, paginationBox);
        finalBox.setAlignment(Pos.CENTER);
        root.setCenter(finalBox);
    }

    /**
     * Creates columns for the TableView.
     *
     * @param tableView The TableView to which columns are added
     */
    private void createTableColumns(TableView<User> tableView) {
        // Create a column for the name
        TableColumn<User, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameColumn.setPrefWidth(100);

        // Create a column for the user
        TableColumn<User, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        firstNameColumn.setPrefWidth(100);

        // Create a column for the mail
        TableColumn<User, String> mailColumn = new TableColumn<>("Mail");
        mailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMail()));
        mailColumn.setPrefWidth(130);

        // Create a column for the birthDate
        TableColumn<User, String> birthDateColumn = new TableColumn<>("Date de naissance");
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirth_date()));
        birthDateColumn.setPrefWidth(120);

        // Create a column for the address
        TableColumn<User, String> addressColumn = new TableColumn<>("Adresse");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        addressColumn.setPrefWidth(120);

        // Create a column for the phone number
        TableColumn<User, String> phoneNumberColumn = new TableColumn<>("Téléphone");
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
        phoneNumberColumn.setPrefWidth(100);

        // Create a column for the number of borrows
        TableColumn<User, String> numberBorrowColumn = new TableColumn<>("Emprunts");
        numberBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber_borrow()));
        numberBorrowColumn.setPrefWidth(80);

        // Add all columns to the table view
        tableView.getColumns().addAll(nameColumn, firstNameColumn, mailColumn, birthDateColumn, addressColumn, phoneNumberColumn, numberBorrowColumn);

        // Resize columns to occupy all available width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Updates the user list displayed in the TableView.
     *
     * @param tableView         The TableView to be updated
     * @param userObservableList The list of users to be displayed
     */
    private void updateUserList(TableView<User> tableView, ObservableList<User> userObservableList) {
        // Calculate the start index of the sublist based on the current page and items per page.
        int fromIndex = currentPage * itemsPerPage;

        // Calculate the end index of the sublist, ensuring not to exceed the list size.
        int toIndex = Math.min(fromIndex + itemsPerPage, userObservableList.size());

        // Update the TableView with a sublist of users corresponding to the current page.
        tableView.setItems(FXCollections.observableArrayList(userObservableList.subList(fromIndex, toIndex)));

        // Update the borrow information for each user
        List<User> userList = UserManager.displayUserList();
        for (User user : userList) {
            // Check if the user is not null
            String mail = user.getMail();
            updateBorrow(mail);
        }
    }

    /**
     * Updates the user list displayed in the TableView.
     *
     * @param tableView The TableView to be updated
     */
    private void updateUserList(TableView<User> tableView) {
        // Retrieve the complete list of users from the UserManager.
        List<User> userList = UserManager.displayUserList();

        // Convert the list of users to an ObservableList
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);

        // Call the previous method to update the TableView with paginated data.
        updateUserList(tableView, userObservableList);
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message The error message to be displayed
     */
    static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the text from the research bar TextField.
     *
     * @return The text from the research bar
     */
    public String getTextFieldResearchBar() {
        return textFieldResearchBar.getText();
    }

    /**
     * Retrieves the Scene of the UsersPage.
     *
     * @return The Scene of the UsersPage
     */
    public Scene getUsersPageScene() {
        return scene;
    }
}

