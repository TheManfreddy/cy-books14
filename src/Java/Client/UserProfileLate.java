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
import java.util.Comparator;
import java.util.List;

public class UserProfileLate extends VBox {
    private Scene scene;

    public UserProfileLate(Stage primaryStage,double width, double height,String mail) throws SQLException {
        // Crée et configure la scène
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Crée un Label pour le titre
        Label titleLabel = new Label("Profil Usager");
        titleLabel.getStyleClass().add("title");

        // Crée un bouton retour
        Button returnButton = new Button("Retour");
        returnButton.getStyleClass().add("button");

        // Configure le bouton retour
        returnButton.setOnAction(e -> {
            UsersLate usersLate = new UsersLate(primaryStage,width, height);
            primaryStage.setScene(usersLate.getUsersLateScene());
        });



        //Crée un Label pour la date du jour
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("label");

        // Crée un conteneur HBox pour le bouton retour et le titre
        HBox topBox = new HBox(100);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton, titleLabel, dateLabel);


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
        userMailLabel.getStyleClass().add("label");

        Label userNameLabel = new Label("Nom :    " + name);
        userNameLabel.getStyleClass().add("label");

        Label userFirstNameLabel = new Label("Prénom :    " + firstName);
        userFirstNameLabel.getStyleClass().add("label");

        Label userBirthDateLabel = new Label("Date de naissance :    " + birthDate);
        userBirthDateLabel.getStyleClass().add("label");

        Label userAddressLabel = new Label("Adresse :    " +address);
        userAddressLabel.getStyleClass().add("label");

        Label userPhoneNumberLabel = new Label("Téléphone :    " + phoneNumber);
        userPhoneNumberLabel.getStyleClass().add("label");

        Label userNumberBorrowLabel = new Label("Nombre d'emprunts :    " + numberBorrow);
        userNumberBorrowLabel.getStyleClass().add("label");

        // Crée un bouton modifications des informations
        Button modifyButton = new Button("Modifications des informations");
        modifyButton.getStyleClass().add("button");

        //Configure le bouton modifier information
        String finalMail = mail;
        modifyButton.setOnAction(e -> {
            ModifyInformation modifyInformation = new ModifyInformation(primaryStage,width, height,  finalMail, name, firstName, birthDate, address, phoneNumber);
            primaryStage.setScene(modifyInformation.getModifyInformationScene());
        });


        HBox userInformationAndModifyButton = new HBox(15);
        VBox userInformationBox = new VBox();
        userInformationBox.getChildren().addAll(userNameLabel,userFirstNameLabel,userMailLabel,userBirthDateLabel,userAddressLabel,userPhoneNumberLabel,userNumberBorrowLabel);
        userInformationAndModifyButton.getChildren().addAll(userInformationBox,modifyButton);

        // Affichage historique emprunts
        userInformation.remove(0);


        // Trier les emprunts par couleur
        userInformation.sort(Comparator.comparing(borrow -> {
            String color = borrow.get(4);
            switch (color) {
                case "green": return 1;
                case "red": return 2;
                case "gray": return 3;
                default: return 4;
            }
        }));


        GridPane borrowsInformationGrid = new GridPane();
        borrowsInformationGrid.setHgap(30);
        borrowsInformationGrid.setVgap(15);
        int col = 0;
        int row = 0;
        List<Borrow> borrows=new ArrayList<>();
        for (int i=1; i<userInformation.size();i++) {
            Borrow borrow = (Borrow) userInformation.get(i);
            borrows.add(borrow);
            VBox borrowInformationBox = new VBox(15);
            String title = borrow.get(0);
            String duration = borrow.get(1);
            String startDate = borrow.get(2);
            String endDate = borrow.get(3);
            String color = borrow.get(4);
            String status = borrow.get(5);
            String isbn = borrow.get(6);

            if (title != null && title.length() > 1 && title.startsWith("[") && title.endsWith("]")) {
                title = title.substring(1, title.length() - 1);
            }

            // Création des labels pour afficher l'historique des emprunts
            Label borrowTitleLabel = new Label("Titre :    " + title);
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
            borrowDurationLabel.getStyleClass().add("label");

            Label borrowStartDateLabel = new Label("Date d'emprunt :    " + startDate);
            borrowStartDateLabel.getStyleClass().add("label");

            Label borrowEndDateLabel = new Label("Date de retour prévue :    " + endDate);
            borrowEndDateLabel.getStyleClass().add("label");

            String queryStatus = "SELECT status FROM borrow WHERE duration>30 AND status=?";

            if (status.equals("0")) {
                // Crée un bouton pour retourner emprunt
                Button returnBorrowButton = new Button("Retour Emprunt");
                returnBorrowButton.getStyleClass().add("button");

                // Configure le bouton retour
                returnBorrowButton.setOnAction(e -> {
                    BorrowManager.returnBorrow(isbn, finalMail);

                    UserProfile userProfile = null;
                    try {
                        userProfile = new UserProfile(primaryStage, width, height, finalMail);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    primaryStage.setScene(userProfile.getUserProfileScene());
                });

                borrowInformationBox.getChildren().addAll(borrowTitleLabel, borrowDurationLabel, borrowStartDateLabel, borrowEndDateLabel, returnBorrowButton);
            } else {
                borrowInformationBox.getChildren().addAll(borrowTitleLabel, borrowDurationLabel, borrowStartDateLabel, borrowEndDateLabel);
            }

            borrowsInformationGrid.add(borrowInformationBox, col, row);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Trier les emprunts par couleur
        borrows.sort(Comparator.comparing(borrow -> {
            String color = borrow.getColor();
            switch (color) {
                case "green": return 1;
                case "red": return 2;
                case "gray": return 3;
                default: return 4;
            }
        }));

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(topBox, userInformationAndModifyButton, borrowsInformationGrid);
        finalBox.setAlignment(Pos.CENTER);

        // Ajout de la boîte finale dans un ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(finalBox);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
    }

    public Scene getUserProfileLateScene() {
        return scene;
    }
}
