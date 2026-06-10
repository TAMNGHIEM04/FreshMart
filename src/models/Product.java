/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class Product {
    private int id; // Thêm mã sản phẩm (MaSP)
    private String name;
    private String category;
    private double price;
    private String description;
    private int quantity;
    private String brand;
    private String image;

    // === Constructors ===
    
    // Constructor đầy đủ (dùng khi lấy từ DB)
    public Product(int id, String name, String category, double price, String description, int quantity, String brand, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.brand = brand;
        this.image = image;
    }

    // Constructor không có ID (dùng khi thêm mới)
    public Product(String name, String category, double price, String description, int quantity, String brand, String image) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.brand = brand;
        this.image = image;
    }

    // Constructor đơn giản (nếu cần)
    public Product(String name, String category, double price) {
        this(name, category, price, "", 0, "", "");
    }

    // === Getter & Setter ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public String getBrand() { return brand; }
    public String getImage() { return image; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return name + " (" + category + ") - " + price + "đ - SL: " + quantity;
    }
}
