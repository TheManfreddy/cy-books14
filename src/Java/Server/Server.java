package Server;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Server {

    private static final List<String> COMMON_PATHS = Arrays.asList(
            "C:\\xampp\\xampp_start.exe",
            "C:\\Developpement\\xampp\\xampp_start.exe"
    );
    public static String XAMPP_START;

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
    public void startXAMPPServices() throws IOException, InterruptedException {
        if (XAMPP_START == null) {
            XAMPP_START = findXamppPath();
            if (XAMPP_START == null) {
                System.out.println("XAMPP path not set. Please specify the path to xampp_start.exe.");
                return;
            }
        }

        if (!isServiceRunning("httpd.exe")) {
            System.out.println("Démarrage de Apache...");
            Runtime.getRuntime().exec(XAMPP_START);
            Thread.sleep(5000); // Attendre quelques secondes pour que le service démarre
        } else {
            System.out.println("Apache est déjà en cours d'exécution.");
        }

        if (!isServiceRunning("mysqld.exe")) {
            System.out.println("Démarrage de MySQL...");
            Runtime.getRuntime().exec(XAMPP_START);
            Thread.sleep(10000); // Attendre plus longtemps pour que MySQL démarre correctement
        } else {
            System.out.println("MySQL est déjà en cours d'exécution.");
        }
    }
    // Méthode pour trouver le chemin de XAMPP automatiquement
    public static String findXamppPath() {
        for (String path : COMMON_PATHS) {
            System.out.println("Checking path: " + path);
            if (Files.exists(Paths.get(path))) {
                System.out.println("Found XAMPP at: " + path);
                return path;
            }
        }
        return null;
    }
}




