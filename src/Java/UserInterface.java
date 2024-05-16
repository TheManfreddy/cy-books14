import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class UserInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Button userButton = new Button("Users");

        userButton.setOnAction(event ->{
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
                Statement stmt = conn.createStatement();

                ResultSet usersResultSet = stmt.executeQuery("SELECT * FROM User");
                VBox userInfoBox = new VBox();
                while (usersResultSet.next()) {
                    // Création des labels pour afficher les informations des utilisateurs
                    String userInfo = "Name: " + usersResultSet.getString("name") +
                            ", First Name: " + usersResultSet.getString("first_name") +
                            ", Address: " + usersResultSet.getString("adress") +
                            ", Number: " + usersResultSet.getInt("number") +
                            ", Number Borrow: " + usersResultSet.getInt("number_borrow");
                    userInfoBox.getChildren().add(new Label(userInfo));
                }
                root.setCenter(userInfoBox);

                /*ResultSet booksResultSet = stmt.executeQuery("SELECT * FROM Book");
                VBox bookInfoBox = new VBox();
                while (booksResultSet.next()) {
                    // Création des labels pour afficher les informations des livres
                    String bookInfo = "Title: " + booksResultSet.getString("title") +
                            ", Kind: " + booksResultSet.getString("kind") +
                            ", Author: " + booksResultSet.getString("author") +
                            ", Time Borrow: " + booksResultSet.getInt("time_borrow");
                    bookInfoBox.getChildren().add(new Label(bookInfo));
                }
                root.setRight(bookInfoBox);*/

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        root.setCenter(userButton);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test Bibli");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}