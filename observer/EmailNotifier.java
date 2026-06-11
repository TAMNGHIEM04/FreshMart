package observer;

public class EmailNotifier implements PaymentObserver {
    public void update() {
        System.out.println("[Email] Thanh toán thành công!");
    }
}