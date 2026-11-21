package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    @Test
    void canWithdrawIfMinimumBalanceIsKept() {
        SavingsAccount account = new SavingsAccount(new AccountNumber("0000000002"),"Petr", 1000, 500);

        boolean result = account.withdraw(400);

        assertTrue(result);
        assertEquals(600, account.getBalance());
    }

    @Test
    void cannotDropBalanceBelowMinimum() {
        SavingsAccount account = new SavingsAccount(new AccountNumber("0000000002"), "Petr", 1000, 500);

        boolean result = account.withdraw(600);

        assertFalse(result, "Нельзя опустить баланс ниже минимума");
        assertEquals(1000, account.getBalance());
    }

    @Test
    void failedWithdrawDoesNotChangeBalance() {
        SavingsAccount account = new SavingsAccount(new AccountNumber("0000000002"), "Petr", 1000, 500);

        boolean result = account.withdraw(2000);

        assertFalse(result);
        assertEquals(1000, account.getBalance(), "При неуспешном снятии баланс не меняется");
    }
}