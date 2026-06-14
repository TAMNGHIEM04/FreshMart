/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package singleton;

import models.Cart;
import models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartSG {
    // Sử dụng inner static class để đảm bảo thread-safe lazy singleton
    private static class Holder {
        private static final CartSG INSTANCE = new CartSG();
    }

    private List<Cart> items;

    private CartSG() {
        items = new ArrayList<>();
    }

    public static CartSG getInstance() {
        return Holder.INSTANCE;
    }

    public List<Cart> getItems() {
        return items;
    }

    public void setItems(List<Cart> newItems) {
        if (newItems != null) {
            this.items = new ArrayList<>(newItems); // tạo bản sao để tránh đột biến bên ngoài
        } else {
            this.items = new ArrayList<>();
        }
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();
    }

    public void clear() {
        items.clear();
    }
    public void add(Product product) {
    for (Cart c : items) {
        if (c.getProduct().getId() == product.getId()) {
            c.setQuantity(c.getQuantity() + 1);
            return;
        }
    }
    items.add(new Cart(product, 1));
}
    // Có thể thêm tiện ích như addItem, removeItem nếu cần
}
