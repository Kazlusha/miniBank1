package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountEqualityTest {

    @Test
    void accountsWithSameNumberAreEqual() {
        DebitAccount a = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);
        DebitAccount b = new DebitAccount(new AccountNumber("0000000001"), "Petr", 5000);

        assertEquals(a, b, "Счета с одинаковым номером равны, даже если владелец и баланс разные");
    }

    @Test
    void accountsWithDifferentNumbersAreNotEqual() {
        DebitAccount a = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);
        DebitAccount b = new DebitAccount(new AccountNumber("0000000002"), "Ivan", 1000);

        assertNotEquals(a, b, "Счета с разными номерами не равны");
    }

    @Test
    void differentAccountTypesWithSameNumberAreEqual() {
        DebitAccount a = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);
        SavingsAccount b = new SavingsAccount(new AccountNumber("0000000001"), "Petr", 5000, 1000);

        assertEquals(a.hashCode(), b.hashCode(),"Равные объекты обязаны иметь одинаковый hashCode");
    }

    @Test
    void equalAccountsHaveSameHashCode() {
        DebitAccount a = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);
        SavingsAccount b = new SavingsAccount(new AccountNumber("0000000001"), "Petr", 5000, 1000);

        assertEquals(a.hashCode(), b.hashCode(),
                "Равные объекты обязаны иметь одинаковый hashCode");
    }

    @Test
    void accountDoesNotEqualNull() {
        DebitAccount account = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);

        assertFalse(account.equals(null), "Объект не равен null");
    }

    @Test
    void accountIsEqualsItself() {
        DebitAccount account = new DebitAccount(new AccountNumber("0000000001"), "Ivan", 1000);

        assertTrue(account.equals(account), "Объект равен сам себе (рефлексивность)");
    }
}
