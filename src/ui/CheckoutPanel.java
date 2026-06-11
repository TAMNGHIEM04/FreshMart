/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import models.Product;
import models.Cart;
import singleton.CartSG;
import strategy.CashPayment;
import strategy.PaymentStrategy;
import strategy.VnPayPayment;
import observer.EmailNotifier;
import observer.PaymentProcessor;
import security.AuthManager;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import models.Product;

public class CheckoutPanel extends JPanel {
    private int userId;
    private final JTextArea summaryArea = new JTextArea(10, 40);
    private final JTextField addressField = new JTextField(30);
    private final JTextField phoneField = new JTextField(15);
    private final JComboBox<String> paymentMethod = new JComboBox<>(new String[]{"COD", "Ví điện tử", "Thẻ ngân hàng"});
    private final JButton confirmBtn = new JButton("Xác nhận thanh toán");
    
    public CheckoutPanel(int userId) {
        this(); // Gọi constructor mặc định
        this.userId = userId;
    }

    public CheckoutPanel() {
        setLayout(new BorderLayout());

        // Form thông tin giao hàng
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Địa chỉ giao hàng:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Phương thức thanh toán:"));
        formPanel.add(paymentMethod);
        formPanel.add(new JLabel());
        formPanel.add(confirmBtn);

        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        confirmBtn.addActionListener(this::processPayment);
    }
    
    
    // Hiển thị giỏ hàng trước khi thanh toán
    public void loadCartSummary() {
    summaryArea.setText("--- XÁC NHẬN ĐƠN HÀNG ---\n");

    // ⚠ Đồng bộ lại giỏ hàng từ DB nếu có userId (sau khi đăng nhập)
    if (userId > 0) {
        List<models.Cart> dbCart = new repository.CartRepository().getCartByCustomer(userId);
        CartSG.getInstance().setItems(dbCart);
    }

    int i = 1;
    double total = 0;

    for (models.Cart c : CartSG.getInstance().getItems()) {
        Product p = c.getProduct();
        int qty = c.getQuantity();
        double lineTotal = p.getPrice() * qty;

        summaryArea.append(String.format("%d. %s x %d = %.2f VND\n", i++, p.getName(), qty, lineTotal));
        total += lineTotal;
    }

    summaryArea.append(String.format("\nTổng cộng: %.2f VND", total));
}



    // Xử lý thanh toán
    private void processPayment(ActionEvent e) {
        // Nếu chưa đăng nhập thì mở dialog đăng nhập
        if (!AuthManager.isUser()) {
            JOptionPane.showMessageDialog(this, "Bạn cần đăng nhập với tài khoản người dùng để thanh toán.");
            new LoginDialog(SwingUtilities.getWindowAncestor(this)).setVisible(true);
            if (!AuthManager.isUser()) {
                return;
            }
        }

        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        if (address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin giao hàng.");
            return;
        }

        // Lựa chọn phương thức thanh toán
        String method = (String) paymentMethod.getSelectedItem();
        PaymentStrategy strategy = switch (method) {
            case "Ví điện tử", "Thẻ ngân hàng" -> new VnPayPayment();
            default -> new CashPayment();
        };

        // Khởi tạo bộ xử lý và thông báo qua email
        PaymentProcessor processor = new PaymentProcessor();
        processor.addObserver(new EmailNotifier());

        boolean success = processor.pay(strategy, CartSG.getInstance().getTotal());

        if (success) {
            JOptionPane.showMessageDialog(this, "Đặt hàng thành công! Thông tin sẽ được gửi qua email.");
            CartSG.getInstance().clear();
            loadCartSummary(); // làm mới hiển thị giỏ hàng
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toán thất bại. Vui lòng thử lại.");
        }
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}