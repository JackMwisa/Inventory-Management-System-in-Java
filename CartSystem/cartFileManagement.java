package CartSystem;

import ProductSystem.*;
import UserSystem.UserFileManagement;
import UserSystem.UserTemp;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static UserSystem.UserFileManagement.currentUser;

public class CartFileManagement {
    public static Path cartFile = Paths.get("src", "FILEDB", "Cart.txt").toAbsolutePath();
    public static Path orderFile = Paths.get("src", "FILEDB", "ORDER.txt").toAbsolutePath();
    public static List<Item> newCart = new ArrayList<>();

    public static void loadingCart() {
        newCart.clear();
        try (Scanner scanner = new Scanner(new FileReader(cartFile.toString()))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                Item item = new Item(
                        line[0], line[1], line[2], line[3], line[4], line[5], line[6],
                        Integer.parseInt(line[7]),
                        Integer.parseInt(line[8]),
                        Integer.parseInt(line[9]),
                        Integer.parseInt(line[10])
                );
                newCart.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error loading cart: " + e.getMessage());
        }
    }

    public static void writeCartToFile() {
        try (FileWriter writer = new FileWriter(cartFile.toString())) {
            for (Item item : newCart) {
                writer.write(item.getTxtFormatCart() + "\n");
            }
            System.out.println("Cart saved successfully.");
            newCart.clear();
            ProductFileManagement.listOfProduct.clear();
        } catch (IOException e) {
            System.out.println("Error writing to the cart file: " + e.getMessage());
        }
    }

    public static void addItemToCart(String username, String itemId, int quantity) {
        ProductFileManagement.loadingProduct();
        loadingCart();

        for (Item item : newCart) {
            if (item.getId().equals(itemId) && item.getUserName().equals(username)) {
                System.out.println("Item already in the cart");
                return;
            }
        }

        for (Item item : ProductFileManagement.listOfProduct) {
            if (item.getId().equals(itemId)) {
                if (item.getQuantity() < quantity) {
                    System.out.println("Insufficient quantity");
                    return;
                }
                Item newItem = new Item(
                        username,
                        item.getId(),
                        item.getProductType(),
                        item.getName(),
                        item.getModel(),
                        item.getManufacturer(),
                        item.getDescription(),
                        item.getSellingPrice(),
                        item.getExpiringYear(),
                        item.getPurchasingPrice(),
                        quantity
                );
                newCart.add(newItem);
                writeCartToFile();
                System.out.println("Item added to the cart successfully");
                return;
            }
        }

        System.out.println("Item not found in product list");
    }

    public static void viewCart() {
        String username = currentUser.getFirst().getUsername();
        loadingCart();

        System.out.println("-- ITEMS IN THE CART --");
        int count = 0;
        for (Item item : newCart) {
            if (item.getUserName().equals(username)) {
                System.out.println(item.getProductInfo());
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No items in the cart");
        }
    }

    public static void removeItemFromCart(String username, String itemId) {
        loadingCart();
        newCart.removeIf(item -> item.getUserName().equals(username) && item.getId().equals(itemId));
        writeCartToFile();
        System.out.println("Item removed from cart successfully");
    }

    public static void finalizePurchase(String username) {
        UserFileManagement.userLoader();
        loadingCart();

        double totalAmount = 0;
        List<Item> finalizedItems = new ArrayList<>();
        StringBuilder remainingCart = new StringBuilder();
        UserTemp currentUserObj = null;

        for (UserTemp user : UserFileManagement.ListOfUsers) {
            if (user.getUsername().equals(username)) {
                currentUserObj = user;
                break;
            }
        }

        if (currentUserObj == null) {
            System.out.println("User not found!");
            return;
        }

        for (Item item : newCart) {
            if (item.getUserName().equals(username)) {
                totalAmount += item.getSellingPrice() * item.getQuantity();
                finalizedItems.add(item);
            } else {
                remainingCart.append(item.getTxtFormatCart()).append("\n");
            }
        }

        if (currentUserObj.getBalance() < totalAmount) {
            System.out.println("Insufficient balance. Total: $" + totalAmount + ", Balance: $" + currentUserObj.getBalance());
            return;
        }

        currentUserObj.setBalanceDefault(currentUserObj.getBalance() - totalAmount);
        UserFileManagement.currentUser.getFirst().setBalanceDefault(currentUserObj.getBalance());

        try (FileWriter writer = new FileWriter(UserFileManagement.userFile.toString())) {
            for (UserTemp user : UserFileManagement.ListOfUsers) {
                writer.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating user file: " + e.getMessage());
            return;
        }

        addToOrders(username, finalizedItems);

        try (FileWriter writer = new FileWriter(cartFile.toString())) {
            writer.write(remainingCart.toString());
        } catch (IOException e) {
            System.out.println("Error updating cart file: " + e.getMessage());
        }

        System.out.println("Purchase complete. Total: $" + totalAmount + ". New balance: $" + currentUserObj.getBalance());
    }

    public static void addToOrders(String username, List<Item> items) {
        try (FileWriter writer = new FileWriter(orderFile.toString(), true)) {
            for (Item item : items) {
                writer.write(item.getTxtFormatCart() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to order file: " + e.getMessage());
        }
    }

    public static void viewOrderHistory(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toString()))) {
            String line;
            boolean found = false;
            System.out.println("Order History for " + username);
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    String[] data = line.split(",");
                    Item item = new Item(
                            data[0], data[1], data[2], data[3], data[4],
                            data[6],
                            Integer.parseInt(data[7]),
                            Integer.parseInt(data[8]),
                            Integer.parseInt(data[9]),
                            Integer.parseInt(data[10])
                    );
                    System.out.println(item.getProductInfo());
                    found = true;
                }
            }
            if (!found) System.out.println("No orders found.");
        } catch (IOException e) {
            System.out.println("Error reading orders: " + e.getMessage());
        }
    }

    public static void viewOrderHistoryAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toString()))) {
            String line;
            System.out.println("All Orders:");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Item item = new Item(
                        data[0], data[1], data[2], data[3], data[4],
                        data[6],
                        Integer.parseInt(data[7]),
                        Integer.parseInt(data[8]),
                        Integer.parseInt(data[9]),
                        Integer.parseInt(data[10])
                );
                System.out.println(item.getProductInfo());
            }
        } catch (IOException e) {
            System.out.println("Error reading all orders: " + e.getMessage());
        }
    }

    public static void getAverage(String username) {
        int total = 0, count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    String[] data = line.split(",");
                    int price = Integer.parseInt(data[7]);
                    int qty = Integer.parseInt(data[10]);
                    total += price * qty;
                    count += qty;
                }
            }
            if (count == 0) {
                System.out.println("No orders found.");
            } else {
                System.out.println("Average Purchase Price: " + (total / count));
            }
        } catch (IOException e) {
            System.out.println("Error calculating average: " + e.getMessage());
        }
    }

    public static void getAverageAll(String username) {
        int total = 0, count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int price = Integer.parseInt(data[7]);
                int qty = Integer.parseInt(data[10]);
                total += price * qty;
                count += qty;
            }
            if (count == 0) {
                System.out.println("No orders found.");
            } else {
                System.out.println("Average Purchase Price (All): " + (total / count));
            }
        } catch (IOException e) {
            System.out.println("Error calculating average for all: " + e.getMessage());
        }
    }
}
