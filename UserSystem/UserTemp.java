package UserSystem;

public class UserTemp {
    private String id;
    private String username;
    private String password;
    private String type;
    private double balance;

    public UserTemp(String id, String username, String password, String type, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.balance = balance;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    // Balance setters
    public void setBalance(double amount) {
        this.balance += amount;
    }

    public void setBalanceDefault(double balance) {
        this.balance = balance;
    }

    public void setBalancePurchase(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + type + "," + balance;
    }
}
