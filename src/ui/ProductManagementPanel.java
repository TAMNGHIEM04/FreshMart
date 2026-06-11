package ui;

import models.Product;
import repository.ProductRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProductManagementPanel extends JPanel {
    private final ProductRepository repo = ProductRepository.getInstance();
    private final DefaultListModel<Product> productListModel = new DefaultListModel<>();
    private final JList<Product> productList = new JList<>(productListModel);
    private final JButton addBtn = new JButton("Thêm");
    private final JButton editBtn = new JButton("Sửa");
    private final JButton deleteBtn = new JButton("Xóa");

    private SearchPanel searchPanel; // Optional liên kết với SearchPanel để cập nhật khi thay đổi

    public ProductManagementPanel() {
        this(null);
    }

    public ProductManagementPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Thiết lập danh sách sản phẩm
        productList.setFont(new Font("Arial", Font.PLAIN, 16));
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(productList), BorderLayout.CENTER);

        // Panel chứa các nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        for (JButton btn : new JButton[]{addBtn, editBtn, deleteBtn}) {
            btn.setFont(btnFont);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        // Gắn sự kiện cho các nút
        addBtn.addActionListener(this::addProduct);
        editBtn.addActionListener(this::editProduct);
        deleteBtn.addActionListener(this::deleteProduct);

        refreshProductList();
    }

    public void refreshProductList() {
        productListModel.clear();
        for (Product p : repo.getAll()) {
            productListModel.addElement(p);
        }
    }

    private void addProduct(ActionEvent e) {
        ProductFormDialog dialog = new ProductFormDialog(null);
        dialog.setVisible(true);

        Product newProduct = dialog.getProduct();
        if (newProduct != null) {
            repo.add(newProduct);
            refreshProductList();
            if (searchPanel != null) searchPanel.refreshSearchResults();
        }
    }

    private void editProduct(ActionEvent e) {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProductFormDialog dialog = new ProductFormDialog(selected);
        dialog.setVisible(true);

        Product updatedProduct = dialog.getProduct();
        if (updatedProduct != null) {
            // Do dữ liệu đã sửa được cập nhật trong DB bởi dialog, chỉ cần reload lại
            refreshProductList();
            if (searchPanel != null) searchPanel.refreshSearchResults();
        }
    }

    private void deleteProduct(ActionEvent e) {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sản phẩm này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            repo.remove(selected);
            refreshProductList();
            if (searchPanel != null) searchPanel.refreshSearchResults();
        }
    }
}
