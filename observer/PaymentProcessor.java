package observer;

import java.util.*;
import strategy.PaymentStrategy;

public class PaymentProcessor {
    private List<PaymentObserver> observers = new ArrayList<>();

    public void addObserver(PaymentObserver obs) {
        observers.add(obs);
    }

    // Trả về true để xác nhận thanh toán thành công
    public boolean pay(PaymentStrategy strategy, double amount) {
        try {
            strategy.pay(amount);
            observers.forEach(PaymentObserver::update);
            return true;
        } catch (Exception e) {
            // Nếu có lỗi xảy ra trong quá trình thanh toán
            return false;
        }
    }
}
