package ui;
import command.AddToCartCommand;
import models.Product;

import javax.swing.*;
import java.awt.*;

public class ProductDetailDialog extends JDialog {
    private final Product product;
    public ProductDetailDialog(Window owner, Product p) {
        super(owner, "Chi tiết sản phẩm", ModalityType.APPLICATION_MODAL);
        this.product = p;
        setup();
    }

    private void setup() {
        setLayout(new BorderLayout(10,10));
        JTextArea info = new JTextArea(productDetails());
        info.setEditable(false);
        add(new JScrollPane(info), BorderLayout.CENTER);

        JButton addBtn = new JButton("Thêm vào giỏ");
        JButton close = new JButton("Đóng");
        JPanel pnl = new JPanel(); pnl.add(addBtn); pnl.add(close);
        add(pnl, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String s = JOptionPane.showInputDialog(this,"Số lượng:", "1");
            int q = Integer.parseInt(s);
            for(int i=0;i<q;i++) new AddToCartCommand(product).execute();
            JOptionPane.showMessageDialog(this,"Đã thêm vào giỏ.");
        });
        close.addActionListener(e -> dispose());

        pack(); setLocationRelativeTo(null); setVisible(true);
    }

    private String productDetails() {
        return String.format(
          "Tên: %s\nGiá: %.0f\nDanh mục: %s\nMô tả: %s\nKho: %d %s",
          product.getName(), product.getPrice(), product.getCategory(),
          product.getDescription(), product.getQuantity(), product.getBrand()
        );
    }
}
