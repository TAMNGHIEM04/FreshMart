/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import security.AuthManager;

public class FreshMartMain extends JFrame {
    public FreshMartMain() {
        super("FreshMart - Giao diện chính");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        HomePanel homePanel = new HomePanel();
        SearchPanel searchPanel = new SearchPanel();
        CartPanel cartPanel = new CartPanel();
        FavoritePanel favoritePanel = new FavoritePanel();
        ProductManagementPanel productManagementPanel = new ProductManagementPanel();
        ReviewManagementPanel reviewManagementPanel = new ReviewManagementPanel();

        // Thêm các tab
        CheckoutPanel checkoutPanel = new CheckoutPanel();
        tabbedPane.addTab("Trang chủ", homePanel);
        tabbedPane.addTab("Tìm kiếm", searchPanel);
        tabbedPane.addTab("Giỏ hàng", cartPanel);
        tabbedPane.addTab("Yêu thích", favoritePanel);
        tabbedPane.addTab("Thanh toán", checkoutPanel); 
        tabbedPane.addTab("QL Sản phẩm", productManagementPanel);
        tabbedPane.addTab("QL Đánh giá", reviewManagementPanel);

        // Xử lý chuyển tab
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(index);

            switch (title) {
                case "Yêu thích" -> {
                    if (!AuthManager.isUser()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để xem danh sách yêu thích.");
                        new LoginDialog(this).setVisible(true);
                        if (!AuthManager.isUser()) {
                            tabbedPane.setSelectedIndex(0); // quay lại trang chủ
                            return;
                        }
                    }
                    favoritePanel.loadFavorites(AuthManager.getUserId());
                }
                case "QL Sản phẩm" -> {
                    if (!AuthManager.isAdmin()) {
                        new LoginDialog(this).setVisible(true);
                        if (!AuthManager.isAdmin()) {
                            tabbedPane.setSelectedIndex(0);
                            return;
                        }
                    }
                    productManagementPanel.refreshProductList();
                }
                case "QL Đánh giá" -> {
                    if (!AuthManager.isAdmin()) {
                        new LoginDialog(this).setVisible(true);
                        if (!AuthManager.isAdmin()) {
                            tabbedPane.setSelectedIndex(0);
                            return;
                        }
                    }
                    reviewManagementPanel.loadReviewList();
                }
                case "Thanh toán" -> {
                    if (!AuthManager.isUser()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để thanh toán.");
                        new LoginDialog(this).setVisible(true);
                        if (!AuthManager.isUser()) {
                            tabbedPane.setSelectedIndex(0); // Quay lại trang chủ
                            return;
                        }
                    }
                    checkoutPanel.setUserId(AuthManager.getUserId()); // Truyền userId
                    checkoutPanel.loadCartSummary();                // Nạp giỏ hàng
                }

            }
        });

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FreshMartMain::new);
    }
}
