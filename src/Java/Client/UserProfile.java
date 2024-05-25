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


/**
 * UserProfile class represents the user profile page.
 */
public class UserProfile extends VBox {
    private Scene scene;

    /**
     * Creates a user profile interface.
     *
     * @param primaryStage The primary stage of the application.
     * @param width         The width of the scene.
     * @param height        The height of the scene.
     * @param mail          The email address of the user.
     * @throws SQLException If a SQL exception occurs.
     */
    public UserProfile(Stage primaryStage, double width, double height, String mail) throws SQLException {
        // Create and configure the scene
        BorderPane root = new BorderPane();
        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("Style/style.css").toExternalForm());

        // Create a Label for the title
        Label titleLabel = new Label("Profil Usager");
        titleLabel.getStyleClass().add("title");

        // Create a return button
        Button returnButton = new Button("⬅");
        returnButton.getStyleClass().add("button-UsersPage");

        // Configure the return button
        returnButton.setOnAction(e -> {
            UsersPage usersPage = new UsersPage(primaryStage, width, height);
            primaryStage.setScene(usersPage.getUsersPageScene());
        });

        // Create an HBox container for the return button and the title
        HBox topBox = new HBox(365);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setStyle("-fx-padding: 20;");
        topBox.getChildren().addAll(returnButton, titleLabel);

        // Create a Label for personal information
        Label personalInfoLabel = new Label("Informations personnelles");
        personalInfoLabel.getStyleClass().add("subtitle");

        // Create a Label for the current date
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        Label dateLabel = new Label(date);
        dateLabel.getStyleClass().add("highlighted-label-gray-date");

        // Create an HBox container for personal information and the date
        HBox topBox2 = new HBox(800);
        topBox2.setAlignment(Pos.CENTER_LEFT);
        topBox2.setStyle("-fx-padding: 40;");
        topBox2.getChildren().addAll(personalInfoLabel, dateLabel);

        // Fetch user information
        List<Object> userInformation = UserManager.displayUser(mail);
        User user = (User) userInformation.get(0);
        mail = user.getMail();
        String name = user.getName();
        String firstName = user.getFirst_name();
        String birthDate = user.getBirth_date();
        String address = user.getAddress();
        String phoneNumber = user.getNumber();
        String numberBorrow = user.getNumber_borrow();

        // Create Labels to display user information
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

        // Create a button to modify user information
        Button modifyButton = new Button("\uD83D\uDD8A");
        modifyButton.getStyleClass().add("button-UsersPage");

        // Configure the modify information button
        String finalMail = mail;
        modifyButton.setOnAction(e -> {
            ModifyInformation modifyInformation = new ModifyInformation(primaryStage, width, height, finalMail, name, firstName, birthDate, address, phoneNumber);
            primaryStage.setScene(modifyInformation.getModifyInformationScene());
        });

        // Create a container for user information and the modify button
        HBox userInformationAndModifyButton = new HBox(15);
        VBox userInformationBox = new VBox();
        userInformationBox.getChildren().addAll(userNameLabel, userFirstNameLabel, userMailLabel, userBirthDateLabel, userAddressLabel, userPhoneNumberLabel, userNumberBorrowLabel);
        userInformationBox.getStyleClass().add("user-info-box");
        userInformationAndModifyButton.getChildren().addAll(userInformationBox, modifyButton);
        userInformationAndModifyButton.setStyle("-fx-padding: 0px 0px 0px 40px;");

        // Create a Label for borrows
        Label borrowsLabel = new Label("Emprunts");
        borrowsLabel.getStyleClass().add("subtitle");

        // Create a container for borrows
        HBox borrowBox = new HBox();
        borrowBox.setAlignment(Pos.CENTER_LEFT);
        borrowBox.setStyle("-fx-padding: 40;");
        borrowBox.getChildren().addAll(borrowsLabel);

        // Display borrow history
        userInformation.remove(0);

        GridPane borrowsInformationGrid = new GridPane();
        borrowsInformationGrid.setHgap(30);
        borrowsInformationGrid.setVgap(15);

        List<Borrow> borrows = new ArrayList<>();

        for (int i = 0; i < userInformation.size(); i++) {
            Borrow borrow = (Borrow) userInformation.get(i);
            borrows.add(borrow);
        }

        borrows.sort(Comparator.comparing(borrow1 -> {
            String color1 = borrow1.getColor();
            switch (color1) {
                case "green":
                    return 1;
                case "red":
                    return 2;
                case "gray":
                    return 3;
                default:
                    return 4;
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

            // Create labels to display borrow history
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

            if (status == 0) {
                // Create a button to return the borrow
                Button returnBorrowButton = new Button("Retour Emprunt");
                returnBorrowButton.getStyleClass().add("button-UsersPage");

                // Configure the return button
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
            borrowsInformationGrid.getStyleClass().add("borrowsGrid");

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        VBox finalBox = new VBox(15);
        finalBox.getChildren().addAll(topBox, topBox2, userInformationAndModifyButton, borrowBox, borrowsInformationGrid);
        finalBox.setAlignment(Pos.CENTER);

        // Add the final box into a ScrollPane
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
     * Get the user profile scene.
     *
     * @return The user profile scene.
     */
    public Scene getUserProfileScene() {
        return scene;
    }
}

