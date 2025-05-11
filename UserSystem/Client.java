package UserSystem;

public class Client extends User {
    private double balance;

    public Client(String username, String password) {
        super(username, password, "CLIENT");
        this.balance = 0;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public void setBalancePurchase(double amount) {
        this.balance -= amount;
    }
}
