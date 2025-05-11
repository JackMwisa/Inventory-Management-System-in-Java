package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String type;
    private double balance;

    public User(int id, String username, String password, String type, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.balance = balance;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getType() { return type; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
