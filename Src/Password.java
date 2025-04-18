import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    private static final String PASSWORDS_FILE = "passwords.txt";

    // Hash a password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Save new user and password (plaintext in file, but hashed for verification)
    public static void saveUser(String username, String password) {
        String hashedPassword = hashPassword(password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSWORDS_FILE, true))) {
            writer.write(username + "," + hashedPassword);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Verify user login (hash input and compare with plaintext password)
    public static boolean verifyUser(String username, String password) {
        String hashedInputPassword = hashPassword(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];

                    // Compare stored plaintext password with hashed input
                    if (storedUsername.equals(username) && storedPassword.equals(hashedInputPassword)) {
                        return true; // Login successful
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error verifying user: " + e.getMessage());
        }
        return false;
    }
}
