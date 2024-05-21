package methods;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {

    private static final String XAMPP_START = "C:\\xampp\\xampp_start.exe";

    // Vérifie si un processus est en cours d'exécution
    private boolean isServiceRunning(String serviceName) throws IOException {
        String line;
        String pidInfo = "";

        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = input.readLine()) != null) {
            pidInfo += line;
        }
        input.close();

        return pidInfo.contains(serviceName);
    }

    // Démarre les services Apache et MySQL
    public void startXAMPPServices() throws IOException {
        if (!isServiceRunning("httpd.exe")) {
            System.out.println("Démarrage de Apache...");
            Runtime.getRuntime().exec(XAMPP_START);
        } else {
            System.out.println("Apache est déjà en cours d'exécution.");
        }

        if (!isServiceRunning("mysqld.exe")) {
            System.out.println("Démarrage de MySQL...");
            Runtime.getRuntime().exec(XAMPP_START);
        } else {
            System.out.println("MySQL est déjà en cours d'exécution.");
        }
    }

    // Démarrage de PhpMyAdmin
    public void startPhpMyAdmin() throws IOException {
        System.out.println("Démarrage de PhpMyAdmin...");
        Runtime.getRuntime().exec("cmd /c start http://localhost/phpmyadmin");
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        Server manager = new Server();
        try {
            manager.startXAMPPServices(); // Démarrage d'Apache et MySQL
            manager.startPhpMyAdmin(); // Démarrage de PhpMyAdmin
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




