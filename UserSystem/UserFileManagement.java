package UserSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class UserFileManagement {

    public static ArrayList<UserTemp> currentUser = new ArrayList<>();
    public static ArrayList<UserTemp> ListOfUsers = new ArrayList<>();

    public static void userLoader() {
        ListOfUsers = UserDataAccess.loadUsers();
    }

    public boolean registerUser() {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        while (true) {
            System.out.println("Choose user type:\n1. Client\n2. Manager");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                user = new Client(username, password);
                break;
            } else if (input.equals("2")) {
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
        UserDataAccess.saveUsers(ListOfUsers);
        System.out.println("User registered successfully.");
        return true;
    }

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
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password) &&
                    user.getType().equals(type)) {
                currentUser.add(user);
                System.out.println("Login successful for " + username);
                return true;
            }
        }

        System.out.println("Login failed. Check credentials.");
        return false;
    }

    public static void viewBalance() {
        if (!currentUser.isEmpty()) {
            System.out.println("Your balance: $" + currentUser.get(0).getBalance());
        } else {
            System.out.println("No user logged in.");
        }
    }

    public static double addBalance(double amount) {
        if (currentUser.isEmpty()) {
            System.out.println("No current user.");
            return 0;
        }

        userLoader(); // Reload full list
        UserTemp activeUser = currentUser.get(0);
        String userId = activeUser.getId();
        activeUser.setBalance(amount); // Add to active session

        for (UserTemp user : ListOfUsers) {
            if (user.getId().equals(userId)) {
                user.setBalance(amount); // Update file list too
                break;
            }
        }

        UserDataAccess.saveUsers(ListOfUsers);
        return activeUser.getBalance();
    }
}
