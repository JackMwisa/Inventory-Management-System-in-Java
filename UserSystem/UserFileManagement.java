package UserSystem;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class UserFileManagement {

    public static Path userFile = Paths.get("src", "FILEDB", "User.txt").toAbsolutePath();
    public static ArrayList<UserTemp> currentUser = new ArrayList<>();
    public static ArrayList<UserTemp> ListOfUsers = new ArrayList<>();

    // Load all users into memory
    public static void userLoader() {
        ListOfUsers.clear();
        try (Scanner input = new Scanner(new FileReader(userFile.toString()))) {
            while (input.hasNextLine()) {
                String[] data = input.nextLine().split(",");
                ListOfUsers.add(new UserTemp(data[0], data[1], data[2], data[3], Double.parseDouble(data[4])));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    // Register a new user
    public boolean registerUser() {
        Scanner scanner = new Scanner(System.in);
        String type = "";
        User user = null;

        while (true) {
            System.out.println("Choose user type:\n1. Client\n2. Manager");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                type = "CLIENT";
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                user = new Client(username, password);
                break;
            } else if (input.equals("2")) {
                type = "MANAGER";
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                user = new Manager(username, password);
                break;
            } else {
                System.out.println("Invalid option. Please choose 1 or 2.");
            }
        }

        userLoader();

        for (UserTemp existing : ListOfUsers) {
            if (existing.getUsername().equals(user.getUsername())) {
                System.out.println("Username already exists.");
                return false;
            }
        }

        ListOfUsers.add(new UserTemp(user.getId(), user.getUsername(), user.getPassword(), user.getType(), 0.0));
        saveAllUsersToFile();
        System.out.println("User registered successfully.");
        return true;
    }

    // Login a user
    public boolean loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose user type:\n1. Client\n2. Manager");
        String input = scanner.nextLine();

        String type = input.equals("1") ? "CLIENT" : input.equals("2") ? "MANAGER" : null;
        if (type == null) {
            System.out.println("Invalid user type.");
            return false;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        userLoader();
        currentUser.clear();

        for (UserTemp user : ListOfUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getType().equals(type)) {
                currentUser.add(user);
                System.out.println("Login successful for " + username);
                return true;
            }
        }

        System.out.println("Login failed. Check credentials.");
        return false;
    }

    // View current balance
    public static void viewBalance() {
        if (!currentUser.isEmpty()) {
            System.out.println("Your balance: $" + currentUser.get(0).getBalance());
        } else {
            System.out.println("No user logged in.");
        }
    }

    // Add to balance
    public static double addBalance(double amount) {
        if (currentUser.isEmpty()) {
            System.out.println("No current user.");
            return 0;
        }

        userLoader();
        UserTemp activeUser = currentUser.get(0);
        String userId = activeUser.getId();
        activeUser.setBalance(amount);

        for (UserTemp user : ListOfUsers) {
            if (user.getId().equals(userId)) {
                user.setBalance(amount);
                break;
            }
        }

        saveAllUsersToFile();
        return activeUser.getBalance();
    }

    // Save all users to file
    private static void saveAllUsersToFile() {
        try (FileWriter writer = new FileWriter(userFile.toString())) {
            for (UserTemp user : ListOfUsers) {
                writer.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing users: " + e.getMessage());
        }
    }
}
