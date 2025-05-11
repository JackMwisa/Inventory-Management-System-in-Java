import dao.UserDAO;
import model.User;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        while (true) {
            System.out.println("\n=== Welcome to Inventory System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // flush

            switch (choice) {
                case 1 -> {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Type (CLIENT/MANAGER): ");
                    String type = scanner.nextLine();

                    boolean success = userDAO.createUser(new User(0, username, password, type, 0));
                    System.out.println(success ? "Registration successful!" : "Failed to register.");
                }
                case 2 -> {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    Optional<User> userOpt = userDAO.findByUsername(username);

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        if (user.getPassword().equals(password)) {
                            System.out.println("Login successful as " + user.getType());
                            // TODO: route to manager/client dashboard
                        } else {
                            System.out.println("Wrong password.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
