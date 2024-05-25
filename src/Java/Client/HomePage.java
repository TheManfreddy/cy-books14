package Client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The HomePage class represents the main entry point of the application, allowing navigation to user and library sections.
 */
public class HomePage extends VBox {
    private Scene scene;

    /**
     * Constructor for HomePage.
     *
     * @param primaryStage the primary stage
     * @param width        the width of the scene
     * @param height       the height of the scene
     */
    public HomePage(Stage primaryStage, double width, double height) {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("CYBOOKS");
        titleLabel.getStyleClass().add("titleConnexion");

        // Create a container for the title and center it
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");
        root.setTop(titleBox);

        // Create a button to navigate to the users section
        Button usersButton = new Button("Usagers");
        usersButton.getStyleClass().add("buttonHome");

        // Create a button to navigate to the library section
        Button libraryButton = new Button("Bibliothèque");
        libraryButton.getStyleClass().add("buttonHome");

        // Create an HBox container and add the components
        HBox hbox = new HBox(70);
        hbox.getChildren().addAll(usersButton, libraryButton);
        hbox.setAlignment(Pos.CENTER);

        // Place the HBox containing the buttons at the center of the BorderPane
        root.setCenter(hbox);

        // Configure the users button to open the UsersPage
        usersButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Configure the library button to open the LibraryPage
        libraryButton.setOnAction(e -> {
            LibraryPage libraryPage = new LibraryPage(primaryStage, width, height);
            primaryStage.setScene(libraryPage.getLibraryPageScene());
        });
    }

    /**
     * Gets the scene for the home page.
     *
     * @return the home page scene
     */
    public Scene getHomePageScene() {
        return scene;
    }
}
