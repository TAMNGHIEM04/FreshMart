/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import security.AuthManager;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    public LoginDialog(Window parent) {
        super(parent, "Đăng nhập", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10, 10));

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("Tài khoản:");
        JTextField usernameField = new JTextField(15);
        JLabel passLabel = new JLabel("Mật khẩu:");
        JPasswordField passwordField = new JPasswordField(15);

        inputPanel.add(userLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passLabel);
        inputPanel.add(passwordField);

        // Panel nút
        JPanel buttonPanel = new JPanel();
        JButton loginBtn = new JButton("Đăng nhập");
        JButton cancelBtn = new JButton("Hủy");
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        // Thêm vào dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setPadding();

        // Xử lý sự kiện nút
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            AuthManager.login(username, password);

            if (AuthManager.isAdmin()) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công (Admin)");
                dispose();
            } else if (AuthManager.isUser()) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công (User)");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc không có quyền.");
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void setPadding() {
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
    }
}
