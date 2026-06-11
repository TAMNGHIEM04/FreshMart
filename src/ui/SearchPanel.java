package ui;
import models.Product;
import repository.CartRepository;
import repository.ProductRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPanel extends JPanel {
    private final JTextField txt = new JTextField(15);
    private final JComboBox<String> cbCat = new JComboBox<>(new String[]{"Tất cả","Rau củ","Thịt","Trái cây"});
    private final JTextField txtPrice = new JTextField(8);
    private final JTextArea res = new JTextArea(10,40);
    private final JButton btnSearch = new JButton("Tìm"), btnView = new JButton("Chi tiết");
    private final JButton btnAdd = new JButton("Thêm giỏ");
    private final ProductRepository pr = ProductRepository.getInstance();
    private final CartRepository cr = new CartRepository();
    private List<Product> sr;
    private final int guest = 1;

    public SearchPanel() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Từ khóa:")); top.add(txt);
        top.add(new JLabel("Loại:")); top.add(cbCat);
        top.add(new JLabel("Giá tối đa:")); top.add(txtPrice);
        top.add(btnSearch); top.add(btnView); top.add(btnAdd);
        add(top,BorderLayout.NORTH);
        res.setEditable(false);
        add(new JScrollPane(res),BorderLayout.CENTER);

        btnSearch.addActionListener(this::search);
        btnView.addActionListener(this::view);
        btnAdd.addActionListener(this::addCart);

        search(null);
    }

    private void search(ActionEvent e){
        try {
            String kw = txt.getText().trim();
            Double mx = txtPrice.getText().isBlank()?null:Double.parseDouble(txtPrice.getText());
            sr = pr.getAll().stream()
               .filter(p-> kw.isEmpty()|| p.getName().toLowerCase().contains(kw))
               .filter(p-> cbCat.getSelectedItem().equals("Tất cả") ||
                          p.getCategory().equals(cbCat.getSelectedItem()))
               .filter(p-> mx==null||p.getPrice()<=mx)
               .collect(Collectors.toList());
            res.setText("");
            if(sr.isEmpty()) res.append("Không tìm thấy");
            else for(int i=0;i<sr.size();i++) res.append((i+1)+". "+sr.get(i)+"\n");
        } catch (Exception ex) {res.setText("Lỗi tìm kiếm");}
    }

    private void view(ActionEvent e){
        String s=JOptionPane.showInputDialog("Nhập thứ tự");
        int i=Integer.parseInt(s)-1;
        new ProductDetailDialog(SwingUtilities.getWindowAncestor(this), sr.get(i));
    }

    private void addCart(ActionEvent e){
        String s=JOptionPane.showInputDialog("Nhập thứ tự");
        int i=Integer.parseInt(s)-1;
        cr.addToCart(guest, sr.get(i), 1);
        JOptionPane.showMessageDialog(this,"Đã thêm");
    }
    public void refreshSearchResults() {
        search(null);
    }
}
