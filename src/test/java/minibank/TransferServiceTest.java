package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    @Test
    void successfulTransferMovesMoneyAndSendsNotification() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10000);
        DebitAccount to = new DebitAccount("B", "Petr", 2000);
        TransferService service = new TransferService(
                new NoCommission(),
                message -> System.out.println("NOTIFIED: " + message)
        );

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result, "Перевод должен пройти успешно");
        assertEquals(7000, from.getBalance());
        assertEquals(5000, to.getBalance());
    }

    @Test
    void failedTransferDoesNotChangeBalances() {
        DebitAccount from = new DebitAccount("A", "Ivan", 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 2000);
        TransferService service = new TransferService(
                new NoCommission(),
                msg -> {}
        );

        boolean result = service.transfer(from, to, 3000);

        assertFalse(result, "Перевод должен быть отклонён");
        assertEquals(1000, from.getBalance());
        assertEquals(2000, to.getBalance());
    }

    @Test
    void cannotTransferToSameAccount() {
        DebitAccount account = new DebitAccount("A", "Ivan", 10000);
        TransferService service = new TransferService(new NoCommission(), msg -> {});

        boolean result = service.transfer(account, account, 1000);

        assertFalse(result, "Перевод на тот же счёт должен быть отклонён");
        assertEquals(10000, account.getBalance());
    }

    @Test
    void cannotTransferNegativeOrZeroAmount() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(new NoCommission(), msg -> {});

        assertFalse(service.transfer(from, to, 0));
        assertFalse(service.transfer(from, to, -500));
        assertEquals(10000, from.getBalance());
        assertEquals(0, to.getBalance());
    }

    @Test
    void commissionIsChargedFromSender() {
        DebitAccount from = new DebitAccount("A", "Ivan", 20000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(
                new PercentCommission(0.01),
                msg -> {}
        );

        boolean result = service.transfer(from, to, 10000);

        assertTrue(result);
        assertEquals(9900, from.getBalance());
        assertEquals(10000, to.getBalance());
    }

    @Test
    void transferFailsIfCannotPayCommission() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10050);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(
                new PercentCommission(0.01),
                msg -> {}
        );

        boolean result = service.transfer(from, to, 10000);

        assertFalse(result, "Перевод должен быть отклонён: не хватает на комиссию");
        assertEquals(10050, from.getBalance(), "Баланс отправителя не должен измениться");
        assertEquals(0, to.getBalance(), "Баланс получателя не должен измениться");
    }
}