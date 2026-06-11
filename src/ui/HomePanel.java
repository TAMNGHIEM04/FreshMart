/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new BorderLayout());
        JLabel welcome = new JLabel("Chào mừng đến với HomeFood", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcome, BorderLayout.CENTER);
    }
}
