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
 * UsersLate class represents the UI for displaying users who have overdue book returns.
 * It extends VBox to organize its components vertically.
 */
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
     * Constructor for UsersLate class.
     *
     * @param primaryStage The primary stage of the application
     * @param width         The width of the scene
     * @param height        The height of the scene
     */
    public UsersLate(Stage primaryStage, double width, double height) {
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

            UserProfileLate userProfileLate;
            try {
                if (!Objects.equals(mail, "")) {
                    if (!UserManager.isUserEmailExists(mail)) {
                        showErrorAlert("L'usager n'existe pas ");
                    } else {
                        userProfileLate = new UserProfileLate(primaryStage, width, height, mail);
                        primaryStage.setScene(userProfileLate.getUserProfileLateScene());
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create a button to add a user
        Button addUserButton = new Button("+");
        addUserButton.getStyleClass().add("button-UsersPage");

        // Create a button to display all users
        Button lateUserButton = new Button("Afficher les retardataires");
        lateUserButton.getStyleClass().add("buttonLate");

        // Configure the button to display all users
        lateUserButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Create an HBox container for the search bar, add user button, and display all users button
        HBox hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 20;");
        hBox.getChildren().addAll(textFieldResearchBar, searchButton, addUserButton, lateUserButton);

        // Configure the add user button to open the add user page
        addUserButton.setOnAction(e -> {
            RegisterUser registerUser = new RegisterUser(primaryStage, width, height);
            primaryStage.setScene(registerUser.getRegisterUserScene());
        });

        // Create a TableView to display user information
        TableView<User> tableView = new TableView<>();
        createTableColumns(tableView);

        // Add an event listener for each row of the table
        tableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    User user = row.getItem();
                    UserProfileLate userProfileLate = null;
                    try {
                        userProfileLate = new UserProfileLate(primaryStage, width, height, user.getMail());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    primaryStage.setScene(userProfileLate.getUserProfileLateScene());
                }
            });
            return row;
        });

        // Create buttons for pagination
        Button prevButton = new Button("<");
        prevButton.getStyleClass().add("button-UsersPage");
        Button nextButton = new Button(">");
        nextButton.getStyleClass().add("button-UsersPage");

        // Configure pagination buttons
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
        List<User> userList = UserManager.displayUserBorrowLateList();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
        totalPages = (int) Math.ceil((double) userList.size() / itemsPerPage);
        updateUserList(tableView, userObservableList);

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(hBox, tableView, paginationBox);
        finalBox.setAlignment(Pos.CENTER);
        root.setCenter(finalBox);
    }

    // Method to create table columns
    private void createTableColumns(TableView<User> tableView) {
        // Create a column for the name
        TableColumn<User, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameColumn.setPrefWidth(100);

        // Create a column for the first name
        TableColumn<User, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        firstNameColumn.setPrefWidth(100);

        // Create a column for the email
        TableColumn<User, String> mailColumn = new TableColumn<>("Mail");
        mailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMail()));
        mailColumn.setPrefWidth(130);

        // Create a column for the birth date
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

    // Method to update the user list in the table view
    private void updateUserList(TableView<User> tableView, ObservableList<User> userObservableList) {
        // Calculate the start index of the sublist based on the current page and items per page
        int fromIndex = currentPage * itemsPerPage;

        // Calculate the end index of the sublist, ensuring not to exceed the list size
        int toIndex = Math.min(fromIndex + itemsPerPage, userObservableList.size());

        // Update the TableView with a sublist of users corresponding to the current page
        tableView.setItems(FXCollections.observableArrayList(userObservableList.subList(fromIndex, toIndex)));

        // Update the borrow status for each user
        List<User> userList = UserManager.displayUserBorrowLateList();
        for (User user : userList) {
            String mail = user.getMail();
            updateBorrow(mail);
        }
    }

    // Overloaded method to update the user list in the table view without providing a new user observable list
    private void updateUserList(TableView<User> tableView) {
        // Retrieve the complete list of users from the UserManager
        List<User> userList = UserManager.displayUserList();

        // Convert the list of users into an ObservableList
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);

        // Call the previous method to update the TableView with paginated data
        updateUserList(tableView, userObservableList);
    }

    // Method to display an error alert
    static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getter for the text field of the search bar
    public String getTextFieldResearchBar() {
        return textFieldResearchBar.getText();
    }

    // Getter for the scene of the UsersLate page
    public Scene getUsersLateScene() {
        return scene;
    }
}


