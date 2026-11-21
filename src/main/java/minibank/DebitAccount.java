package minibank;

public class DebitAccount extends BankAccount {

    public DebitAccount(AccountNumber number, String owner, double initialBalance) {
        super(number, owner, initialBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (this.getBalance() >= amount) {
            setBalance(this.getBalance() - amount);
            return true;
        }
        return false;
    }
}