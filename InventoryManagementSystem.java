import java.util.Scanner;
import UserSystem.*;
import ProductSystem.*;
import CartSystem.*;
import static CartSystem.cartFileManagement.*;
import static UserSystem.UserFileManagement.*;

public class InventoryManagementSystem {

    public static UserFileManagement user = new UserFileManagement();
    public static productFileManagement product = new productFileManagement();
    public static cartFileManagement cart = new cartFileManagement();

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
                    if(userCheck){
                        String type = UserFileManagement.currentUser.getFirst().getType();
                        String username = UserFileManagement.currentUser.getFirst().getUsername();
                        if(type.equals("CLIENT")){
                            userMenu(username);
                        }else if(type.equals("MANAGER")){
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
                    break;
            }
        }
    }

    // Updated userMenu with username as a parameter
    public static void userMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("\n====================");
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
            scanner.nextLine(); // Handle the newline character

            switch (choice) {
                case 1:
                    productFileManagement.viewProducts();
                    break;
                case 2:
                    System.out.println("Enter the product ID to add to the cart: ");
                    String productId = scanner.nextLine();
                    System.out.println("Enter the quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Handle newline
                    cartFileManagement.addItemToCart(username, productId, quantity);
                    break;
                case 3:
                    System.out.println("Enter the product ID you want to remove: ");
                    String productToRemove = scanner.nextLine();
                    cartFileManagement.removeItemFromCart(username, productToRemove);
                    break;
                case 4:
                    cartFileManagement.viewCart();
                    break;
                case 5:
                    viewBalance();
                    break;
                case 6:
                    cart.finalizePurchase(username);
                    break;
                case 7:
                    System.out.println("Enter the amount to add to your balance: ");
                    double amountToAdd = scanner.nextDouble();
                    scanner.nextLine(); // Handle newline
                    System.out.println("Your new balance is: " + addBalance(amountToAdd));
                    break;
                case 8:
                    cart.getAverage(username);
                    break;
                case 9:
                    cart.viewOrderHistory(username);
                    break;
                case 10:
                    // search for item in the cart
                    break;
                case 11:
                    exit = true;
                    break;
                case 12:
                    System.out.println("Exiting to the main menu...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    // Updated managementMenu
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
                    productFileManagement.viewProducts();
                    break;
                case 2:
                    productFileManagement.addNewProduct();
                    break;
                case 3:
                    System.out.println("Enter item Id: ");
                    String itemId = scanner.nextLine();
                    productFileManagement.updateProduct(itemId);
                    break;
                case 4:
                    System.out.println("Enter item Id: ");
                    String itemIdTorm = scanner.nextLine();
                    productFileManagement.removeProduct(itemIdTorm);
                    break;
                case 5:
                    // warehouse account
                    break;
                case 6:
                    System.out.println("Enter product name to search: ");
                    String productName = scanner.nextLine();
                    //        productFileManagement.searchProduct(productName);
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

    // Helper method to fetch the logged-in username
    public static String getLoggedInUsername() {
        for (UserTemp userTemp : currentUser) {
            return userTemp.getUsername(); // Assuming currentUser contains the logged-in user
        }
        return "";
    }
}