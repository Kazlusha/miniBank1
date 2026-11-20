package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    @Test
    void cannotWithdrawBelowMinimumBalance() {
        SavingsAccount account = new SavingsAccount("222", "Petr", 1000, 500);

        boolean result = account.withdraw(600);

        assertFalse(result, "Снятие ниже минимального остатка должно вернуть false");
        assertEquals(1000, account.getBalance(), "Баланс не должен измениться");
    }

    @Test
    void canWithdrawDownToMinimumBalance() {
        SavingsAccount account = new SavingsAccount("222", "Petr", 1000, 500);

        boolean result = account.withdraw(500);

        assertTrue(result, "Снятие до минимума должно пройти успешно");
        assertEquals(500, account.getBalance(), "Баланс должен стать 500");
    }

    @Test
    void canWithdrawWhenBalanceStaysAboveMinimum() {
        SavingsAccount account = new SavingsAccount("222", "Petr", 1000, 500);

        boolean result = account.withdraw(300);

        assertTrue(result, "Снятие выше минимума должно пройти успешно");
        assertEquals(700, account.getBalance());
    }

    @Test
    void cannotWithdrawNegativeOrZeroAmount() {
        SavingsAccount account = new SavingsAccount("222", "Petr", 1000, 500);

        assertFalse(account.withdraw(0));
        assertFalse(account.withdraw(-100));
        assertEquals(1000, account.getBalance());
    }
}