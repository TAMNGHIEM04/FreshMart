package strategy;

/**
 * Strategy pattern interface for payment methods.
 */
public interface PaymentStrategy {
    /**
     * Thực hiện thanh toán với số tiền cụ thể.
     *
     * @param amount Số tiền cần thanh toán (đơn vị: VND)
     */
    void pay(double amount);
}
