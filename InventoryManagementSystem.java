import java.util.Scanner;
import UserSystem.*;
import ProductSystem.*;
import CartSystem.*;

import static CartSystem.CartFileManagement.*;
import static UserSystem.UserFileManagement.*;

public class InventoryManagementSystem {

    public static UserFileManagement user = new UserFileManagement();
    public static ProductFileManagement product = new ProductFileManagement();
    public static CartFileManagement cart = new CartFileManagement();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    boolean userCheck = user.loginUser();
                    if (userCheck) {
                        String type = currentUser.getFirst().getType();
                        String username = currentUser.getFirst().getUsername();
                        if (type.equalsIgnoreCase("CLIENT")) {
                            userMenu(username);
                        } else if (type.equalsIgnoreCase("MANAGER")) {
                            managementMenu(username);
                        }
                    }
                    break;
                case 2:
                    user.registerUser();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Items in warehouse");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Items in the Cart");
            System.out.println("5. View Remaining Balance");
            System.out.println("6. Purchase Items");
            System.out.println("7. Add balance");
            System.out.println("8. Average Purchase price");
            System.out.println("9. History");
            System.out.println("10. Search");
            System.out.println("11. Back");
            System.out.println("12. Exit to Main Menu");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Handle newline

            switch (choice) {
                case 1:
                    product.viewProducts();
                    break;
                case 2:
                    System.out.print("Enter the product ID to add to the cart: ");
                    String productId = scanner.nextLine();
                    System.out.print("Enter the quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    cart.addItemToCart(username, productId, quantity);
                    break;
                case 3:
                    System.out.print("Enter the product ID you want to remove: ");
                    String productToRemove = scanner.nextLine();
                    cart.removeItemFromCart(username, productToRemove);
                    break;
                case 4:
                    cart.viewCart();
                    break;
                case 5:
                    viewBalance();
                    break;
                case 6:
                    cart.finalizePurchase(username);
                    break;
                case 7:
                    System.out.print("Enter the amount to add to your balance: ");
                    double amountToAdd = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Your new balance is: " + addBalance(amountToAdd));
                    break;
                case 8:
                    cart.getAverage(username);
                    break;
                case 9:
                    cart.viewOrderHistory(username);
                    break;
                case 10:
                    // Optional: Implement cart search functionality
                    break;
                case 11:
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void managementMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Management Menu ---");
            System.out.println("1. View Items");
            System.out.println("2. Add New Item");
            System.out.println("3. Update the Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Check Balance");
            System.out.println("6. Search Product");
            System.out.println("7. Order History");
            System.out.println("8. Calculate Average Purchase");
            System.out.println("9. Exit to Main Menu");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Handle newline

            switch (choice) {
                case 1:
                    product.viewProducts();
                    break;
                case 2:
                    product.addNewProduct();
                    break;
                case 3:
                    System.out.print("Enter item Id: ");
                    String itemId = scanner.nextLine();
                    product.updateProduct(itemId);
                    break;
                case 4:
                    System.out.print("Enter item Id: ");
                    String itemIdToRemove = scanner.nextLine();
                    product.removeProduct(itemIdToRemove);
                    break;
                case 5:
                    viewBalance();
                    break;
                case 6:
                    System.out.print("Enter product name to search: ");
                    String productName = scanner.nextLine();
                    product.searchProduct(productName); // Uncomment if implemented
                    break;
                case 7:
                    cart.viewOrderHistoryAll();
                    break;
                case 8:
                    cart.getAverageAll(username);
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static String getLoggedInUsername() {
        for (UserTemp userTemp : currentUser) {
            return userTemp.getUsername();
        }
        return "";
    }
}
