package UserSystem;

public class Manager extends User {
    private double accountBalance;

    public Manager(String username, String password) {
        super(username, password, "MANAGER");
        this.accountBalance = 0;
    }

    @Override
    public double getBalance() {
        return accountBalance;
    }

    @Override
    public void setBalance(double amount) {
        this.accountBalance += amount;
    }

    @Override
    public void setBalancePurchase(double amount) {
        this.accountBalance -= amount;
    }
}
