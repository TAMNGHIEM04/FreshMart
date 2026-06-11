/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import models.Cart;
import models.Product;
import repository.CartRepository;
import security.AuthManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CartPanel extends JPanel {
    private final JTextArea txt = new JTextArea(10, 40);
    private final JButton btnUpd = new JButton("Cập nhật");
    private final JButton btnDel = new JButton("Xóa");
    private final JButton btnRef = new JButton("Tải lại");
    private final JButton btnFav = new JButton("❤️ Yêu thích");

    private final CartRepository cr = new CartRepository();
 

    private List<Cart> items;
    private final int guest = 1;

    public CartPanel() {
        setLayout(new BorderLayout());
        txt.setEditable(false);
        add(new JScrollPane(txt), BorderLayout.CENTER);

        JPanel bot = new JPanel();
        bot.add(btnUpd);
        bot.add(btnDel);
        bot.add(btnRef);
        bot.add(btnFav);
        add(bot, BorderLayout.SOUTH);

        btnUpd.addActionListener(e -> update());
        btnDel.addActionListener(e -> delete());
        btnRef.addActionListener(e -> load());
        btnFav.addActionListener(e -> saveToFavorites());

        load();
    }

    private void load() {
        items = cr.getCartByCustomer(guest);
        txt.setText("--- Giỏ hàng ---\n");
        for (int i = 0; i < items.size(); i++) {
            Cart c = items.get(i);
            Product p = c.getProduct();
            txt.append((i + 1) + ". " + p.getName() + " x " + c.getQuantity() + " = " + (p.getPrice() * c.getQuantity()) + "\n");
        }
        txt.append("\nTổng = " + cr.getTotal(guest));
    }

    private void update() {
        String s = JOptionPane.showInputDialog("STT SL mới (vd: 1 3)");
        if (s == null || s.trim().isEmpty()) return;
        try {
            String[] a = s.trim().split("\\s+");
            int index = Integer.parseInt(a[0]) - 1;
            int qty = Integer.parseInt(a[1]);
            if (index < 0 || index >= items.size() || qty <= 0) throw new Exception();

            Cart c = items.get(index);
            cr.updateQty(guest, c.getProduct().getId(), qty);
            load();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
        }
    }

    private void delete() {
        String s = JOptionPane.showInputDialog("STT muốn xóa:");
        if (s == null || s.trim().isEmpty()) return;
        try {
            int index = Integer.parseInt(s.trim()) - 1;
            if (index < 0 || index >= items.size()) throw new Exception();

            Cart c = items.get(index);
            cr.removeItem(guest, c.getProduct().getId());
            load();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "STT không hợp lệ.");
        }
    }

    private void saveToFavorites() {
        if (!AuthManager.isUser()) {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để lưu vào danh sách yêu thích.");
            new LoginDialog(SwingUtilities.getWindowAncestor(this)).setVisible(true);
            if (!AuthManager.isUser()) return;
        }

        String s = JOptionPane.showInputDialog("Nhập STT sản phẩm muốn yêu thích:");
        if (s == null || s.trim().isEmpty()) return;

        try {
            int index = Integer.parseInt(s.trim()) - 1;
            if (index < 0 || index >= items.size()) throw new Exception();

            Product p = items.get(index).getProduct();
            int userId = AuthManager.getUserId();
            boolean success = cr.addToWishlist(userId, p.getId());

            if (success) {
                JOptionPane.showMessageDialog(this, "Đã thêm vào yêu thích.");
            } else {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong danh sách yêu thích.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "STT không hợp lệ.");
        }
    }

    private void openCheckout() {
        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống, không thể thanh toán.");
            return;
        }

        if (!AuthManager.isUser()) {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để tiếp tục thanh toán.");
            new LoginDialog(SwingUtilities.getWindowAncestor(this)).setVisible(true);
            if (!AuthManager.isUser()) return;
        }

        int userId = AuthManager.getUserId();
        cr.transferCart(guest, userId);

        CheckoutDialog dialog = new CheckoutDialog(SwingUtilities.getWindowAncestor(this), userId);
        dialog.setVisible(true);

        load(); // reload cart
    }

    // Dialog thanh toán
    public static class CheckoutDialog extends JDialog {
        public CheckoutDialog(Window owner, int userId) {
            super(owner, "Xác nhận thanh toán", Dialog.ModalityType.APPLICATION_MODAL);
            setSize(600, 450);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout());

            CheckoutPanel panel = new CheckoutPanel(userId);
            panel.loadCartSummary();
            add(panel, BorderLayout.CENTER);
        }
    }
}

