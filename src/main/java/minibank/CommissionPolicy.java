package minibank;

public interface CommissionPolicy {
    double calculate(double amount);
}