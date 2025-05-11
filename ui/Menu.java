package ui;

import dao.*;
import model.*;
import service.PurchaseService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final CartDAO cartDAO = new CartDAO();
    private static final OrderDAO orderDAO = new OrderDAO();
    private static final PurchaseService purchaseService = new PurchaseService(cartDAO, orderDAO, productDAO, userDAO);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Register\n2. Login\n0. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Type (CLIENT/MANAGER): ");
        String type = scanner.nextLine();

        boolean created = userDAO.createUser(new User(0, username, password, type, 0));
        System.out.println(created ? "User registered." : "Registration failed.");
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        Optional<User> optionalUser = userDAO.findByUsername(username);

        if (optionalUser.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        User user = optionalUser.get();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return;
        }

        if (user.getType().equals("MANAGER")) {
            managerMenu(user);
        } else {
            clientMenu(user);
        }
    }

    private static void managerMenu(User user) {
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("1. Add Product\n2. View Products\n3. Delete Product\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Type: "); String type = scanner.nextLine();
        System.out.print("Model: "); String model = scanner.nextLine();
        System.out.print("Manufacturer: "); String manufacturer = scanner.nextLine();
        System.out.print("Description: "); String description = scanner.nextLine();
        System.out.print("Selling Price: "); double sellingPrice = scanner.nextDouble();
        System.out.print("Purchasing Price: "); double purchasingPrice = scanner.nextDouble();
        System.out.print("Expiring Year: "); int year = scanner.nextInt();
        System.out.print("Quantity: "); int quantity = scanner.nextInt();
        scanner.nextLine();

        Product product = new Product(name, type, model, manufacturer, description, sellingPrice, purchasingPrice, year, quantity);
        productDAO.addProduct(product);
        System.out.println("Product added.");
    }

    private static void viewProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            products.forEach(System.out::println);
        }
    }

    private static void deleteProduct() {
        System.out.print("Product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (productDAO.deleteProduct(id)) System.out.println("Deleted.");
        else System.out.println("Delete failed.");
    }

    private static void clientMenu(User user) {
        while (true) {
            System.out.println("\nClient Menu:");
            System.out.println("1. View Products\n2. Add to Cart\n3. View Cart\n4. Purchase\n5. View Orders\n6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addToCart(user);
                    break;
                case 3:
                    viewCart(user);
                    break;
                case 4:
                    purchaseService.purchaseCart(user);
                    break;
                case 5:
                    viewOrders(user);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addToCart(User user) {
        System.out.print("Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        cartDAO.addToCart(new CartItem(user.getId(), productId, quantity));
        System.out.println("Added to cart.");
    }

    private static void viewCart(User user) {
        List<CartItem> cart = cartDAO.getCartItemsByUserId(user.getId());
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem item : cart) {
                Product product = productDAO.getProductById(item.getProductId());
                System.out.println(product + " | Quantity: " + item.getQuantity());
            }
        }
    }

    private static void viewOrders(User user) {
        List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
        } else {
            orders.forEach(order -> System.out.printf("ProductID: %d | Qty: %d | Total: $%.2f | Date: %s%n",
                    order.getProductId(), order.getQuantity(), order.getTotalPrice(), order.getCreatedAt()));
        }
    }
}
