package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DebitAccountTest {

    @Test
    void cannotWithdrawMoreThanBalance() {
        DebitAccount account = new DebitAccount("111", "Ivan", 1000);

        boolean result = account.withdraw(1500);

        assertFalse(result, "Снятие сверх баланса должно вернуть false");
        assertEquals(1000, account.getBalance(), "Баланс не должен измениться");
    }

    @Test
    void canWithdrawExactBalance() {
        DebitAccount account = new DebitAccount("111", "Ivan", 1000);

        boolean result = account.withdraw(1000);

        assertTrue(result, "Снятие всей суммы должно пройти успешно");
        assertEquals(0, account.getBalance(), "Баланс должен стать 0");
    }

    @Test
    void cannotWithdrawNegativeOrZeroAmount() {
        DebitAccount account = new DebitAccount("111", "Ivan", 1000);

        assertFalse(account.withdraw(0), "Снятие нуля должно вернуть false");
        assertFalse(account.withdraw(-100), "Снятие отрицательной суммы должно вернуть false");
        assertEquals(1000, account.getBalance(), "Баланс не должен измениться");
    }

    @Test
    void depositShouldIncreaseBalance() {
        DebitAccount account = new DebitAccount("111", "Ivan", 1000);

        account.deposit(500);

        assertEquals(1500, account.getBalance(), "Пополнение должно увеличить баланс");
    }

    @Test
    void constructorShouldThrowOnNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class,
                () -> new DebitAccount("111", "Ivan", -100),
                "Отрицательный начальный баланс должен вызывать исключение");
    }
}