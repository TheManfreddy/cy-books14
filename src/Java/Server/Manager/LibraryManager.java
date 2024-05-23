package Server.Manager;

import java.sql.*;

public class LibraryManager {
    public static boolean validateLogin(String login, String password) {
        String url = "jdbc:mysql://localhost:3307/bibli";
        String user = "root";  // Nom d'utilisateur de la base de données
        String pass = "";  // Mot de passe de la base de données

        String query = "SELECT * FROM library WHERE login = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Définir les paramètres de la requête
            stmt.setString(1, login);
            stmt.setString(2, password);

            // Exécuter la requête
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // L'utilisateur existe
                    return true;
                }
            }
        } catch (SQLException e) {
            // Gestion des erreurs de connexion et d'exécution de la requête
            e.printStackTrace();
            // Vous pouvez également utiliser un journal de bord (logger) pour enregistrer les erreurs
        }

        // Retourner false si l'utilisateur n'existe pas ou en cas d'erreur
        return false;
    }

}
