package minibank;

public class CreditAccount extends BankAccount {
    private final double creditLimit;

    public CreditAccount(String number, String owner, double initialBalance, double creditLimit) {
        super(number, owner, initialBalance);
        if (creditLimit < 0) {
            throw new IllegalArgumentException("Credit limit cannot be negative");
        }
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (this.getBalance() - amount >= -creditLimit) {
            setBalance(this.getBalance() - amount);
            return true;
        }
        return false;
    }

    public double getCreditLimit() {
        return creditLimit;
    }
}