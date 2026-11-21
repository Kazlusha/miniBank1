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
        DebitAccount debit = new DebitAccount("001", "Ivan", 1000);
        SavingsAccount savings = new SavingsAccount("001", "Petr", 5000, 1000);
        CreditAccount credit = new CreditAccount("001", "Oleg", 2000, 5000);

        assertEquals(debit, savings, "Debit и Savings с одинаковым номером равны");
        assertEquals(debit, credit, "Debit и Credit с одинаковым номером равны");
        assertEquals(savings, credit, "Savings и Credit с одинаковым номером равны");
    }

    @Test
    void equalAccountsHaveSameHashCode() {
        DebitAccount a = new DebitAccount("001", "Ivan", 1000);
        SavingsAccount b = new SavingsAccount("001", "Petr", 5000, 1000);

        assertEquals(a.hashCode(), b.hashCode(),
                "Равные объекты обязаны иметь одинаковый hashCode");
    }

    @Test
    void accountIsNotEqualToNull() {
        DebitAccount account = new DebitAccount("001", "Ivan", 1000);

        assertNotEquals(account, null);
    }

    @Test
    void accountIsEqualToItself() {
        DebitAccount account = new DebitAccount("001", "Ivan", 1000);

        assertEquals(account, account, "Объект равен сам себе (рефлексивность)");
    }
}
