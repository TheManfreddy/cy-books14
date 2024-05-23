package Client;

import Server.Manager.BorrowManager;
import Server.Manager.UserManager;
import Server.Models.Borrow;
import Server.Models.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class UsersLate extends VBox {
    private Scene scene;
    private TextField textFieldResearchBar;
    private int currentPage = 0;
    private final int itemsPerPage = 5;
    private int totalPages;

    private Stage primaryStage;
    private double width;
    private double height;

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

        // Crée un conteneur pour le titre et le centre
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-padding: 20;");
        root.setTop(titleBox);

        // Crée un bouton retour
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button");

        // Crée un conteneur HBox pour le bouton retour et le titre
        HBox topBox = new HBox(100);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton);

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, width, height);
            primaryStage.setScene(homePage.getHomePageScene());
        });

        // Crée un champ de texte pour la barre de recherche
        textFieldResearchBar = new TextField();
        textFieldResearchBar.setPromptText("Rechercher un usager");
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

        // Création des labels pour afficher les légendes
        String legend = "Prénom" + "        " + "Nom" + "        " + "Mail" + "        "
                + "Date de naissance" + "        " + "Adresse" + "        " + "Téléphone" + "        "
                + "Emprunts" + "        ";
        Label legendLabel = new Label(legend);
        legendLabel.getStyleClass().add("label");

        // Crée un conteneur HBox pour les légendes
        HBox legendBox = new HBox();
        legendBox.setAlignment(Pos.CENTER_LEFT);
        legendBox.setStyle("-fx-padding: 20;");
        legendBox.getChildren().add(legendLabel);
        legendBox.getStyleClass().add("data-box");

        // Affiche les utilisateurs
        VBox usersInformationBox = new VBox(15);

        // Crée des boutons pour la pagination
        Button prevButton = new Button("<");
        prevButton.getStyleClass().add("button-UsersPage");
        Button nextButton = new Button(">");
        nextButton.getStyleClass().add("button-UsersPage");

        prevButton.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateUserList(usersInformationBox);
            }
        });

        nextButton.setOnAction(e -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                updateUserList(usersInformationBox);
            }
        });

        HBox paginationBox = new HBox(20, prevButton, nextButton);
        paginationBox.setAlignment(Pos.CENTER);
        paginationBox.setStyle("-fx-padding: 20;");

        // Initialisation de la liste d'utilisateurs
        List<User> userList = UserManager.displayUserBorrowLateList();
        totalPages = (int) Math.ceil((double) userList.size() / itemsPerPage);
        updateUserList(usersInformationBox, userList);

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(topBox, hBox, legendBox, usersInformationBox, paginationBox);
        finalBox.setAlignment(Pos.CENTER);
        root.setLeft(finalBox);
    }

    private void updateUserList(VBox usersInformationBox, List<User> userList) {
        usersInformationBox.getChildren().clear();

        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, userList.size());

        for (int i = start; i < end; i++) {
            User user = userList.get(i);
            HBox userInformationBox = new HBox(15);
            String mail = user.getMail();
            String name = user.getName();
            String firstName = user.getFirst_name();
            String birthDate = user.getBirth_date();
            String address = user.getAddress();
            String phoneNumber = user.getNumber();
            String numberBorrow = user.getNumber_borrow();

            // Mise à jour de la durée des emprunts
            BorrowManager.updateBorrow(mail);

            // Création des labels pour afficher les informations des utilisateurs
            Label userMailLabel = new Label(mail);
            userMailLabel.getStyleClass().add("label");

            Label userNameLabel = new Label(name);
            userNameLabel.getStyleClass().add("label");

            Label userFirstNameLabel = new Label(firstName);
            userFirstNameLabel.getStyleClass().add("label");

            Label userBirthDateLabel = new Label(birthDate);
            userBirthDateLabel.getStyleClass().add("label");

            Label userAddressLabel = new Label(address);
            userAddressLabel.getStyleClass().add("label");

            Label userPhoneNumberLabel = new Label(phoneNumber);
            userPhoneNumberLabel.getStyleClass().add("label");

            Label userNumberBorrowLabel = new Label(numberBorrow);
            userNumberBorrowLabel.getStyleClass().add("label");

            // Crée le bouton pour afficher l'oeil
            Button eyeButton = new Button("\uD83D\uDC40");
            eyeButton.getStyleClass().add("button");

            userInformationBox.getChildren().addAll(userNameLabel, userFirstNameLabel, userMailLabel, userBirthDateLabel, userAddressLabel, userPhoneNumberLabel, userNumberBorrowLabel, eyeButton);
            usersInformationBox.getChildren().add(userInformationBox);
            userInformationBox.getStyleClass().add("date-box-value");

            // Configure le bouton oeil pour ouvrir le profil usager
            eyeButton.setOnAction(e -> {
                UserProfile userProfile = null;
                try {
                    userProfile = new UserProfile(primaryStage, width, height, mail);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                primaryStage.setScene(userProfile.getUserProfileScene());
            });
        }
    }

    private void updateUserList(VBox usersInformationBox) {
        List<User> userList = UserManager.displayUserBorrowLateList();
        updateUserList(usersInformationBox, userList);
    }

    public String getTextFieldResearchBar() {
        return textFieldResearchBar.getText();
    }

    public Scene getUsersLateScene() {
        return scene;
    }
}
