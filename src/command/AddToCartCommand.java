/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import models.Product;
import singleton.CartSG;

public class AddToCartCommand implements CartCommand {
    private final Product product;
    private final int quantity;

    public AddToCartCommand(Product product) {
        this(product, 1); // Mặc định là 1 sản phẩm
    }

    public AddToCartCommand(Product product, int quantity) {
        this.product = product;
        this.quantity = Math.max(quantity, 1); // Đảm bảo số lượng >= 1
    }

    @Override
    public void execute() {
        for (int i = 0; i < quantity; i++) {
            CartSG.getInstance().add(product); // ✅ dùng add 1 sản phẩm mỗi lần
        }
    }
}
