package minibank;

public class SavingsAccount extends BankAccount {
    private final double minimumBalance;

    public SavingsAccount(String number, String owner, double initialBalance, double minimumBalance) {
        super(number, owner, initialBalance);
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Минимальный баланс не может быть отрицательным");
        }
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        if (this.getBalance() - amount >= minimumBalance) {
            setBalance(this.getBalance() - amount);
            return true;
        }
        return false;
    }
}