package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountEqualityTest {

    @Test
    void accountsWithSameNumberAreEqual() {
        DebitAccount a = new DebitAccount("001", "Ivan", 1000);
        DebitAccount b = new DebitAccount("001", "Petr", 5000);

        assertEquals(a, b, "Счета с одинаковым номером равны, даже если владелец и баланс разные");
    }

    @Test
    void accountsWithDifferentNumbersAreNotEqual() {
        DebitAccount a = new DebitAccount("001", "Ivan", 1000);
        DebitAccount b = new DebitAccount("002", "Ivan", 1000);

        assertNotEquals(a, b, "Счета с разными номерами не равны");
    }

    @Test
    void differentAccountTypesWithSameNumberAreEqual() {
        DebitAccount a = new DebitAccount("001", "Ivan", 1000);
        SavingsAccount b = new SavingsAccount("001", "Petr", 5000, 1000);

        assertEquals(a.hashCode(), b.hashCode(),"Равные объекты обязаны иметь одинаковый hashCode");
    }

    @Test
    void equalAccountsHaveSameHashCode() {
        DebitAccount a = new DebitAccount("001", "Ivan", 1000);
        SavingsAccount b = new SavingsAccount("001", "Petr", 5000, 1000);

        assertEquals(a.hashCode(), b.hashCode(),
                "Равные объекты обязаны иметь одинаковый hashCode");
    }

    @Test
    void accountDoesNotEqualNull() {
        DebitAccount account = new DebitAccount("001", "Ivan", 1000);

        assertFalse(account.equals(null), "Объект не равен null");
    }

    @Test
    void accountIsEqualsItself() {
        DebitAccount account = new DebitAccount("001", "Ivan", 1000);

        assertTrue(account.equals(account), "Объект равен сам себе (рефлексивность)");
    }
}
