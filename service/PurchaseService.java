package service;

import dao.CartDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import model.CartItem;
import model.Order;
import model.Product;
import model.User;

import java.util.List;

public class PurchaseService {
    private final CartDAO cartDAO;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    public PurchaseService(CartDAO cartDAO, OrderDAO orderDAO, ProductDAO productDAO, UserDAO userDAO) {
        this.cartDAO = cartDAO;
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    public boolean purchaseCart(User user) {
        List<CartItem> cartItems = cartDAO.getCartItemsByUserId(user.getId());

        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return false;
        }

        double totalCost = 0;
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProductId());
            if (product == null || product.getQuantity() < item.getQuantity()) {
                System.out.println("Product not available or insufficient stock: " + item.getProductId());
                return false;
            }
            totalCost += product.getSellingPrice() * item.getQuantity();
        }

        if (user.getBalance() < totalCost) {
            System.out.println("Insufficient balance. Total required: $" + totalCost);
            return false;
        }

        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProductId());
            int newStock = product.getQuantity() - item.getQuantity();
            productDAO.updateQuantity(product.getId(), newStock);

            double totalPrice = product.getSellingPrice() * item.getQuantity();
            orderDAO.recordOrder(new Order(user.getId(), product.getId(), item.getQuantity(), totalPrice));
        }

        cartDAO.clearCartForUser(user.getId());

        double updatedBalance = user.getBalance() - totalCost;
        user.setBalance(updatedBalance);
        userDAO.updateBalance(user.getId(), updatedBalance);

        System.out.println("Purchase successful! Total: $" + totalCost);
        return true;
    }
}