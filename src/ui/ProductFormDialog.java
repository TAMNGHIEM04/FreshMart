package ui;

import models.Product;
import repository.ProductRepository;
import javax.swing.*;
import java.awt.*;

public class ProductFormDialog extends JDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(10);
    private final JTextField categoryField = new JTextField(15);
    private final JTextField quantityField = new JTextField(5);
    private final JTextField brandField = new JTextField(15);
    private final JTextField imageField = new JTextField(20);
    private final JTextArea descArea = new JTextArea(3, 20);

    private Product product;

    public ProductFormDialog(Product existing) {
        setTitle(existing == null ? "Thêm sản phẩm" : "Sửa sản phẩm");
        setModal(true);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        nameField.setFont(fieldFont);
        priceField.setFont(fieldFont);
        categoryField.setFont(fieldFont);
        quantityField.setFont(fieldFont);
        brandField.setFont(fieldFont);
        imageField.setFont(fieldFont);
        descArea.setFont(fieldFont);

        int y = 0;
        formAdd(form, gbc, y++, "Tên sản phẩm:", nameField);
        formAdd(form, gbc, y++, "Giá:", priceField);
        formAdd(form, gbc, y++, "Danh mục:", categoryField);
        formAdd(form, gbc, y++, "Số lượng:", quantityField);
        formAdd(form, gbc, y++, "Thương hiệu:", brandField);
        formAdd(form, gbc, y++, "Hình ảnh (URL hoặc đường dẫn):", imageField);
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        JScrollPane scrollPane = new JScrollPane(descArea);
        scrollPane.setPreferredSize(new Dimension(200, 60));
        form.add(scrollPane, gbc);

        add(form, BorderLayout.CENTER);

        JButton saveBtn = new JButton("💾 Lưu");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveBtn);
        add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> onSave(existing));

        if (existing != null) {
            nameField.setText(existing.getName());
            priceField.setText(String.valueOf(existing.getPrice()));
            categoryField.setText(existing.getCategory());
            quantityField.setText(String.valueOf(existing.getQuantity()));
            brandField.setText(existing.getBrand());
            imageField.setText(existing.getImage());
            descArea.setText(existing.getDescription());
        }

        pack();
        setLocationRelativeTo(null);
    }

    private void onSave(Product existing) {
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String category = categoryField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String brand = brandField.getText().trim();
        String image = imageField.getText().trim();
        String description = descArea.getText().trim();

        if (name.isEmpty() || priceText.isEmpty() || category.isEmpty() ||
            quantityText.isEmpty() || brand.isEmpty() || image.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống trường nào.");
            return;
        }

        double price;
        int quantity;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá hoặc số lượng không hợp lệ.");
            return;
        }

        if (existing == null) {
            product = new Product(name, category, price, description, quantity, brand, image);
        } else {
            existing.setName(name);
            existing.setCategory(category);
            existing.setPrice(price);
            existing.setDescription(description);
            existing.setQuantity(quantity);
            existing.setBrand(brand);
            existing.setImage(image);
            
            ProductRepository.getInstance().update(existing);
        }

        dispose();
    }

    public Product getProduct() {
        return product;
    }

    private void formAdd(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}
