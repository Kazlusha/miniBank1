package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditAccountTest {

    @Test
    void canWithdrawWithinCreditLimit() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        boolean result = account.withdraw(4000);

        assertTrue(result, "Снятие в пределах кредитного лимита должно пройти успешно");
        assertEquals(-3000, account.getBalance());
    }

    @Test
    void cannotWithdrawBeyondCreditLimit() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);
        account.withdraw(4000);

        boolean result = account.withdraw(3000);

        assertFalse(result, "Снятие сверх лимита должно вернуть false");
        assertEquals(-3000, account.getBalance(), "Баланс не должен измениться");
    }

    @Test
    void canWithdrawExactlyToCreditLimit() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        boolean result = account.withdraw(6000);

        assertTrue(result);
        assertEquals(-5000, account.getBalance());
    }

    @Test
    void cannotWithdrawNegativeOrZeroAmount() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        assertFalse(account.withdraw(0));
        assertFalse(account.withdraw(-100));
        assertEquals(1000, account.getBalance());
    }

    @Test
    void constructorShouldThrowOnNegativeCreditLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> new CreditAccount("333", "Oleg", 1000, -500));
    }
}