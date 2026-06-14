package models;

public class Cart {
    private int id;
    private Product product;
    private int quantity;

    public Cart(int id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    // Nếu không cần `id` có thể bỏ constructor này:
    public Cart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " - SL: " + quantity + " - Giá: " + product.getPrice() * quantity + "đ";
    }
}
