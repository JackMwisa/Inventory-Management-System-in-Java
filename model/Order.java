package model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private Timestamp createdAt;

    public Order(int id, int userId, int productId, int quantity, double totalPrice, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Order(int userId, int productId, int quantity, double totalPrice) {
        this(0, userId, productId, quantity, totalPrice, null);
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public Timestamp getCreatedAt() { return createdAt; }
}