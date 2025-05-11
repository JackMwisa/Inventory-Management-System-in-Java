package UserSystem;

import java.util.UUID;

public abstract class User {
    protected String id;
    protected String username;
    protected String password;
    protected String type;

    public User(String username, String password, String type) {
        this.id = UUID.randomUUID().toString().substring(0, 4);
        this.username = username;
        this.password = password;
        this.type = type;
    }

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

    public abstract double getBalance();
    public abstract void setBalance(double amount);
    public abstract void setBalancePurchase(double amount);
}
