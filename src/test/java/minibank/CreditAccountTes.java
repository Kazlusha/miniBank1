package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditAccountTest {

    @Test
    void accountCanGoNegative() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        boolean result = account.withdraw(2000);

        assertTrue(result);
        assertEquals(-1000, account.getBalance(), "Баланс может быть отрицательным");
    }

    @Test
    void canUseCreditLimit() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        boolean result = account.withdraw(5000);

        assertTrue(result);
        assertEquals(-4000, account.getBalance());
    }

    @Test
    void cannotExceedCreditLimit() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);

        boolean result = account.withdraw(7000);

        assertFalse(result, "Нельзя превысить кредитный лимит");
        assertEquals(1000, account.getBalance());
    }

    @Test
    void failedWithdrawDoesNotChangeBalance() {
        CreditAccount account = new CreditAccount("333", "Oleg", 1000, 5000);
        account.withdraw(5000);

        boolean result = account.withdraw(2000);

        assertFalse(result);
        assertEquals(-4000, account.getBalance(), "При неуспешном снятии баланс не меняется");
    }
}