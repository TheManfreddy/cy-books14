package Server;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * The Server class provides methods to manage XAMPP services.
 * It can check if certain services are running and start them if they are not.
 */
public class Server {

    // List of common paths where XAMPP might be installed
    private static final List<String> COMMON_PATHS = Arrays.asList(
            "C:\\xampp\\xampp_start.exe",
            "C:\\Developpement\\xampp\\xampp_start.exe"
    );
    public static String XAMPP_START;

    /**
     * Checks if a given service (process) is currently running.
     *
     * @param serviceName the name of the service (process) to check
     * @return true if the service is running, false otherwise
     * @throws IOException if an I/O error occurs
     */
    private boolean isServiceRunning(String serviceName) throws IOException {
        String line;
        String pidInfo = "";

        // Execute the Windows tasklist command to get the list of running processes
        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

        // Read the output of the tasklist command
        while ((line = input.readLine()) != null) {
            pidInfo += line;
        }
        input.close();

        // Check if the service name is present in the list of running processes
        return pidInfo.contains(serviceName);
    }

    /**
     * Starts the XAMPP services (Apache and MySQL) if they are not already running.
     *
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public void startXAMPPServices() throws IOException, InterruptedException {
        // If XAMPP_START is not set, try to find the XAMPP path automatically
        if (XAMPP_START == null) {
            XAMPP_START = findXamppPath();
            if (XAMPP_START == null) {
                System.out.println("XAMPP path not set. Please specify the path to xampp_start.exe.");
                return;
            }
        }

        // Check and start Apache if it's not running
        if (!isServiceRunning("httpd.exe")) {
            System.out.println("Starting Apache...");
            Runtime.getRuntime().exec(XAMPP_START);
            Thread.sleep(5000); // Wait a few seconds for the service to start
        } else {
            System.out.println("Apache is already running.");
        }

        // Check and start MySQL if it's not running
        if (!isServiceRunning("mysqld.exe")) {
            System.out.println("Starting MySQL...");
            Runtime.getRuntime().exec(XAMPP_START);
            Thread.sleep(10000); // Wait longer for MySQL to start properly
        } else {
            System.out.println("MySQL is already running.");
        }
    }

    /**
     * Attempts to find the path to the XAMPP start executable automatically.
     *
     * @return the path to xampp_start.exe if found, otherwise null
     */
    public static String findXamppPath() {
        // Iterate over the list of common paths to check for the existence of xampp_start.exe
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
