import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

public class UserInterface extends Application {

    public static void addBorrow(int isbn, String idUser){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            // Récupérer le nombre d'emprunts de l'utilisateur
            String getUserBorrowCountSql = "SELECT number_borrow FROM user WHERE mail = ?";
            pstmt = conn.prepareStatement(getUserBorrowCountSql);
            pstmt.setString(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            int numberBorrow = 0;

            if (rs.next()) {
                numberBorrow = rs.getInt("number_borrow");
                System.out.println("Nombre d'emprunts actuels: " + numberBorrow);
            } else {
                System.out.println("Utilisateur non trouvé.");
                return;
            }

            // Vérifier si l'utilisateur peut emprunter plus de livres
            if (numberBorrow >= 5) {
                System.out.println("L'utilisateur a atteint le nombre maximal d'emprunts.");
                return;
            } else {
                // Ajouter un nouvel emprunt
                Borrow.registerBorrow(isbn, idUser);

                // Mettre à jour le nombre d'emprunts de l'utilisateur
                String updateUserBorrowCountSql = "UPDATE user SET number_borrow = number_borrow + 1 WHERE mail = ?";
                pstmt = conn.prepareStatement(updateUserBorrowCountSql);
                pstmt.setString(1, idUser);

                int updateRows = pstmt.executeUpdate();
                if (updateRows > 0) {
                    System.out.println("Nombre d'emprunts mis à jour avec succès!");
                } else {
                    System.out.println("Échec de la mise à jour du nombre d'emprunts.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



    public static List<List<String>> historyBorrow(String idUser){

        List<List<String>> listOfBorrows = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");

            String statusSql = "SELECT isbn,duration,start_date,end_date,status FROM Borrow WHERE idUser=?";
            pstmt = conn.prepareStatement(statusSql);
            // Attribution des valeurs aux paramètres
            pstmt.setString(1,idUser);

            ResultSet r = pstmt.executeQuery();
            while (r.next()) {  // Déplacer le curseur au premier enregistrement

                List<String> list1 = new ArrayList<>();

                int isbn = r.getInt("isbn");
                int duration = r.getInt("duration");
                Date start_date = r.getDate("start_date");
                Date end_date = r.getDate("end_date");
                int status = r.getInt("status");

                list1.add(String.valueOf(isbn));
                list1.add(String.valueOf(duration));
                list1.add(String.valueOf(start_date));
                list1.add(String.valueOf(end_date));


                System.out.println("ISBN: "+ isbn + " Duration: " + duration + " Start Date: "+ start_date + " End Date: " + end_date);

                if(status == 0 && duration>30){
                    System.out.println("status: in progress " + " LATE!");
                    list1.add("rouge");
                }
                if(status == 0 && duration<=30){
                    System.out.println("status: in progress " + "OKAY");
                    list1.add("gris");
                }
                if(status == 1){
                    System.out.println("status: done " + "OKAY");
                }


            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



    /****************************/


    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Crée un Label pour le titre
        Label titleLabel = new Label("CYBOOKS");
        titleLabel.getStyleClass().add("title");

        // Crée un conteneur pour le titre et le centre
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");  // Ajoute du padding autour du titre
        root.setTop(titleBox);

        // Crée un Label pour l'identifiant
        Label labelIdentifiant = new Label("Identifiant :");
        labelIdentifiant.getStyleClass().add("label");

        // Crée un champ de texte pour l'identifiant
        TextField textFieldIdentifiant = new TextField();
        textFieldIdentifiant.setPromptText("Identifiant");
        textFieldIdentifiant.getStyleClass().add("text-field");

        // Crée un conteneur VBox pour l'identifiant et son champ de texte
        VBox identifiantBox = new VBox(5, labelIdentifiant, textFieldIdentifiant);
        identifiantBox.setAlignment(Pos.CENTER_LEFT);

        // Crée un Label pour le mot de passe
        Label labelPassword = new Label("Mot de passe :");
        labelPassword.getStyleClass().add("label");

        // Crée un champ de texte pour le mot de passe
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.getStyleClass().add("password-field");

        // Crée un conteneur VBox pour le mot de passe et son champ de texte
        VBox passwordBox = new VBox(5, labelPassword, passwordField);
        passwordBox.setAlignment(Pos.CENTER_LEFT);

        // Crée un bouton pour se connecter
        Button userButton = new Button("Se connecter");
        userButton.getStyleClass().add("button");

        // Crée un conteneur VBox et y ajoute les composants
        VBox vbox = new VBox(15); // 15 est l'espacement entre les éléments
        vbox.getChildren().addAll(identifiantBox, passwordBox, userButton);
        vbox.getStyleClass().add("container");
        vbox.setAlignment(Pos.CENTER);

        // Place le VBox contenant le champ de texte et le bouton au centre du BorderPane
        root.setCenter(vbox);

        // Configure l'action du bouton
        userButton.setOnAction(event -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bibli", "root", "");
                Statement stmt = conn.createStatement();

                ResultSet usersResultSet = stmt.executeQuery("SELECT * FROM user");
                VBox userInfoBox = new VBox(10);
                while (usersResultSet.next()) {
                    // Création des labels pour afficher les informations des utilisateurs
                    String userInfo = "Name: " + usersResultSet.getString("name") +
                            ", First Name: " + usersResultSet.getString("first_name") +
                            ", Address: " + usersResultSet.getString("adress") +
                            ", Phone Number: " + usersResultSet.getInt("phonenumber") +
                            ", Number Borrow: " + usersResultSet.getInt("number_borrow");
                    Label userInfoLabel = new Label(userInfo);
                    userInfoLabel.getStyleClass().add("label");
                    userInfoBox.getChildren().add(userInfoLabel);
                }
                root.setCenter(userInfoBox);

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Crée et configure la scène
        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Test Bibli");
        primaryStage.show();
    }

    public static void main(String[] args) {
        historyBorrow("albertroger@gmail.com");
        launch(args);
    }
}

