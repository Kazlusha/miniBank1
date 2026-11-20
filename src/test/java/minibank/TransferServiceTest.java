package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private final CommissionPolicy noCommission = new NoCommission();
    private final NotificationService silentNotification = message -> {};


    @Test
    void successfulTransferChangesBothBalances() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10000);
        DebitAccount to = new DebitAccount("B", "Petr", 2000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result, "Перевод должен пройти успешно");
        assertEquals(7000, from.getBalance(), "Баланс отправителя должен уменьшиться");
        assertEquals(5000, to.getBalance(), "Баланс получателя должен увеличиться");
    }

    @Test
    void failedTransferDoesNotChangeAnyBalance() {
        DebitAccount from = new DebitAccount("A", "Ivan", 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 2000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 3000);

        assertFalse(result, "Перевод должен быть отклонён");
        assertEquals(1000, from.getBalance(), "Баланс отправителя не должен измениться");
        assertEquals(2000, to.getBalance(), "Баланс получателя не должен измениться");
    }

    @Test
    void cannotTransferNegativeAmount() {
        DebitAccount from = new DebitAccount("A", "Ivan", 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 1000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, -500);

        assertFalse(result);
        assertEquals(1000, from.getBalance());
        assertEquals(1000, to.getBalance());
    }

    @Test
    void cannotTransferZeroAmount() {
        DebitAccount from = new DebitAccount("A", "Ivan", 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 1000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 0);

        assertFalse(result);
        assertEquals(1000, from.getBalance());
        assertEquals(1000, to.getBalance());
    }

    @Test
    void cannotTransferToSameAccount() {
        DebitAccount account = new DebitAccount("A", "Ivan", 10000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(account, account, 1000);

        assertFalse(result, "Перевод самому себе запрещён");
        assertEquals(10000, account.getBalance());
    }

    @Test
    void commissionIsChargedFromSender() {
        DebitAccount from = new DebitAccount("A", "Ivan", 20000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(new PercentCommission(0.01), silentNotification);

        boolean result = service.transfer(from, to, 10000);

        assertTrue(result);
        assertEquals(9900, from.getBalance(), "С отправителя списана сумма перевода + комиссия");
    }

    @Test
    void recipientReceivesExactlyTransferAmount() {
        DebitAccount from = new DebitAccount("A", "Ivan", 20000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(new PercentCommission(0.01), silentNotification);

        boolean result = service.transfer(from, to, 10000);

        assertTrue(result);
        assertEquals(10000, to.getBalance(), "Получатель получает ровно сумму перевода, без вычета комиссии");
    }

    @Test
    void transferFailsIfCannotCoverCommission() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10050);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        TransferService service = new TransferService(new PercentCommission(0.01), silentNotification);

        boolean result = service.transfer(from, to, 10000);

        assertFalse(result, "Перевод должен быть отклонён: не хватает на комиссию");
        assertEquals(10050, from.getBalance(), "Баланс отправителя не должен измениться");
        assertEquals(0, to.getBalance(), "Баланс получателя не должен измениться");
    }

    @Test
    void transferFromDebitToDebit() {
        DebitAccount from = new DebitAccount("A", "Ivan", 5000);
        DebitAccount to = new DebitAccount("B", "Petr", 1000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 2000);

        assertTrue(result);
        assertEquals(3000, from.getBalance());
        assertEquals(3000, to.getBalance());
    }

    @Test
    void transferFromDebitToSavings() {
        DebitAccount from = new DebitAccount("A", "Ivan", 5000);
        SavingsAccount to = new SavingsAccount("B", "Petr", 1000, 500);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 2000);

        assertTrue(result);
        assertEquals(3000, from.getBalance());
        assertEquals(3000, to.getBalance());
    }

    @Test
    void transferFromCreditToDebit() {
        CreditAccount from = new CreditAccount("A", "Ivan", 1000, 5000);
        DebitAccount to = new DebitAccount("B", "Petr", 1000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result);
        assertEquals(-2000, from.getBalance(), "Кредитный счёт ушёл в минус");
        assertEquals(4000, to.getBalance());
    }

    @Test
    void transferFromSavingsToDebit() {
        SavingsAccount from = new SavingsAccount("A", "Ivan", 5000, 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 1000);
        TransferService service = new TransferService(noCommission, silentNotification);

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result);
        assertEquals(2000, from.getBalance());
        assertEquals(4000, to.getBalance());
    }

    @Test
    void successfulTransferSendsExactlyOneNotification() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        FakeNotificationService notificationService = new FakeNotificationService();
        TransferService service = new TransferService(new NoCommission(), notificationService);

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result);
        assertEquals(1, notificationService.getNotificationCount(),
                "После успешного перевода должно быть ровно одно уведомление");
    }

    @Test
    void failedTransferDoesNotSendNotification() {
        DebitAccount from = new DebitAccount("A", "Ivan", 1000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        FakeNotificationService notificationService = new FakeNotificationService();
        TransferService service = new TransferService(new NoCommission(), notificationService);

        boolean result = service.transfer(from, to, 3000);

        assertFalse(result);
        assertEquals(0, notificationService.getNotificationCount(),
                "При неуспешном переводе уведомление не отправляется");
    }

    @Test
    void notificationMessageContainsTransferAmount() {
        DebitAccount from = new DebitAccount("A", "Ivan", 10000);
        DebitAccount to = new DebitAccount("B", "Petr", 0);
        FakeNotificationService notificationService = new FakeNotificationService();
        TransferService service = new TransferService(new NoCommission(), notificationService);

        boolean result = service.transfer(from, to, 3000);

        assertTrue(result);
        String expectedMessage = "Трансфер 3000.0 завершен";
        assertEquals(expectedMessage, notificationService.getLastMessage(),
                "Текст уведомления должен соответствовать ожидаемому");
    }
}