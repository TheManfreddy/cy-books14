package Client;

import Server.Manager.BorrowManager;
import Server.Manager.UserManager;
import Server.Models.Book;
import Server.Models.Borrow;
import Server.Models.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static Server.Data.APIBNF.retrieveBook_isbn;


public class UserProfileLate extends VBox {
    private Scene scene;

    /**
     * @param primaryStage
     * @param width
     * @param height
     * @param mail
     * @throws SQLException
     */
    public UserProfileLate(Stage primaryStage, double width, double height, String mail) throws SQLException {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Profil Usager");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            UsersLate usersLate = new UsersLate(primaryStage, width, height);
            primaryStage.setScene(usersLate.getUsersLateScene());
        });

        // Crée un conteneur HBox pour le bouton retour, le titre
        HBox topBox = new HBox(365);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton, titleLabel);

        //Crée un Label pour Informations personnelles
        Label personalInfoLabel = new Label("Informations personnelles");
        personalInfoLabel.getStyleClass().add("subtitle");

        // Crée un Label pour la date du jour
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("highlighted-label-gray-date");


        //Crée un conteneur Hbox pour les informations personnelles et la date
        HBox topBox2 = new HBox(800);
        topBox2.setAlignment(Pos.CENTER_LEFT);
        topBox2.setStyle("-fx-padding: 40;");
        topBox2.getChildren().addAll(personalInfoLabel,dateLabel);

        // Création du label pour afficher les informations des utilisateurs
        List<Object> userInformation = UserManager.displayUser(mail);
        User user = (User) userInformation.get(0);
        mail = user.getMail();
        String name = user.getName();
        String firstName = user.getFirst_name();
        String birthDate= user.getBirth_date();
        String address = user.getAddress();
        String phoneNumber = user.getNumber();
        String numberBorrow = user.getNumber_borrow();

        Label userMailLabel = new Label("Mail :    " + mail);
        userMailLabel.getStyleClass().add("labelUser");

        Label userNameLabel = new Label("Nom :    " + name);
        userNameLabel.getStyleClass().add("labelUser");

        Label userFirstNameLabel = new Label("Prénom :    " + firstName);
        userFirstNameLabel.getStyleClass().add("labelUser");

        Label userBirthDateLabel = new Label("Date de naissance :    " + birthDate);
        userBirthDateLabel.getStyleClass().add("labelUser");

        Label userAddressLabel = new Label("Adresse :    " + address);
        userAddressLabel.getStyleClass().add("labelUser");

        Label userPhoneNumberLabel = new Label("Téléphone :    " + phoneNumber);
        userPhoneNumberLabel.getStyleClass().add("labelUser");

        Label userNumberBorrowLabel = new Label("Nombre d'emprunts :    " + numberBorrow);
        userNumberBorrowLabel.getStyleClass().add("labelUser");

        // Crée un bouton modifications des informations
        Button modifyButton = new Button("\uD83D\uDD8A");
        modifyButton.getStyleClass().add("button-UsersPage");

        // Configure le bouton modifier information
        String finalMail = mail;
        modifyButton.setOnAction(e -> {
            ModifyInformation modifyInformation = new ModifyInformation(primaryStage, width, height, finalMail, name, firstName, birthDate, address, phoneNumber);
            primaryStage.setScene(modifyInformation.getModifyInformationScene());
        });

        //Crée un conteneur pour les informations personnelles et le bouton modification de retour
        HBox userInformationAndModifyButton = new HBox(15);
        VBox userInformationBox = new VBox();
        userInformationBox.getChildren().addAll(userNameLabel, userFirstNameLabel, userMailLabel, userBirthDateLabel, userAddressLabel, userPhoneNumberLabel, userNumberBorrowLabel);
        userInformationBox.getStyleClass().add("user-info-box");
        userInformationAndModifyButton.getChildren().addAll(userInformationBox, modifyButton);
        userInformationAndModifyButton.setStyle("-fx-padding: 0px 0px 0px 40px;");

        //Crée un Label pour Emprunts
        Label borrowsLabel= new Label("Emprunts");
        borrowsLabel.getStyleClass().add("subtitle");

        //Création d'un contenur pour Emprunts
        HBox borrowBox = new HBox();
        borrowBox.setAlignment(Pos.CENTER_LEFT);
        borrowBox.setStyle("-fx-padding: 40;");
        borrowBox.getChildren().addAll(borrowsLabel);

        // Affichage historique emprunts
        userInformation.remove(0);

        GridPane borrowsInformationGrid = new GridPane();
        borrowsInformationGrid.setHgap(30);
        borrowsInformationGrid.setVgap(15);

        List<Borrow> borrows=new ArrayList<>();

        for (int i = 0; i < userInformation.size(); i++) {
            Borrow borrow = (Borrow) userInformation.get(i);
            borrows.add(borrow);
        }

        borrows.sort(Comparator.comparing(borrow1 -> {
            String color1 = borrow1.getColor();
            switch (color1) {
                case "green": return 1;
                case "red": return 2;
                case "gray": return 3;
                default: return 4;
            }
        }));


        int col = 0;
        int row = 0;



        for (Borrow borrow : borrows) {
            VBox borrowInformationBox = new VBox(15);
            borrowInformationBox.getStyleClass().add("borrowBox");

            String isbn = borrow.getIsbn();
            Book book = retrieveBook_isbn(isbn);
            String title = book.getTitle();
            int duration = borrow.getDuration();
            Date startDate = borrow.getStart_date();
            Date endDate = borrow.getEnd_date();
            String color = borrow.getColor();
            int status = borrow.getStatus();

            // Création des labels pour afficher l'historique des emprunts
            Label borrowTitleLabel = new Label("Titre :    " + title);
            borrowTitleLabel.getStyleClass().add("borrowTitle");
            if (color.equals("red")) {
                borrowTitleLabel.getStyleClass().add("highlighted-label-red");
            }
            if (color.equals("green")) {
                borrowTitleLabel.getStyleClass().add("highlighted-label-green");
            }
            if (color.equals("gray")) {
                borrowTitleLabel.getStyleClass().add("highlighted-label-gray");
            }

            Label borrowDurationLabel = new Label("Durée :    " + duration);
            borrowDurationLabel.getStyleClass().add("labelUser");

            Label borrowStartDateLabel = new Label("Date d'emprunt :    " + startDate);
            borrowStartDateLabel.getStyleClass().add("labelUser");

            Label borrowEndDateLabel = new Label("Date de retour prévue :    " + endDate);
            borrowEndDateLabel.getStyleClass().add("labelUser");

            String queryStatus = "SELECT status FROM borrow WHERE duration>30 AND status=?";

            if (status==0) {
                // Crée un bouton pour retourner emprunt
                Button returnBorrowButton = new Button("Retour Emprunt");
                returnBorrowButton.getStyleClass().add("button-UsersPage");

                // Configure le bouton retour
                returnBorrowButton.setOnAction(e -> {
                    BorrowManager.returnBorrow(isbn, finalMail);

                    UserProfile userProfile = null;
                    try {
                        userProfile = new UserProfile(primaryStage, width, height, finalMail);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    primaryStage.setScene(userProfileLate.getUserProfileLateScene());
                });

                borrowInformationBox.getChildren().addAll(borrowTitleLabel, borrowDurationLabel, borrowStartDateLabel, borrowEndDateLabel, returnBorrowButton);
            } else {
                borrowInformationBox.getChildren().addAll(borrowTitleLabel, borrowDurationLabel, borrowStartDateLabel, borrowEndDateLabel);
            }

            borrowsInformationGrid.add(borrowInformationBox, col, row);
            borrowsInformationGrid.getStyleClass().add("borrowsGrid");

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }



        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(topBox,topBox2,userInformationAndModifyButton, borrowBox, borrowsInformationGrid);
        finalBox.setAlignment(Pos.CENTER);

        // Ajout de la boîte finale dans un ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(finalBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar only when needed
        scrollPane.getStyleClass().add("scroll-pane"); // Apply custom style
        root.setCenter(scrollPane);
    }

    /**
     * @return
     */
    public Scene getUserProfileLateScene() {
        return scene;
    }
}
