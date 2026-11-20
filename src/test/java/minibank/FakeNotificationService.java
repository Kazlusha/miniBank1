package minibank;

public class FakeNotificationService implements NotificationService {
    private String lastMessage;
    private int notificationCount;

    @Override
    public void notify(String message) {
        this.lastMessage = message;
        this.notificationCount++;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}