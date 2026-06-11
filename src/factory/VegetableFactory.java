package factory;

import models.Product;

public class VegetableFactory extends ProductFactory {
    @Override
    public Product createProduct(String name, double price) {
        String description = "Sản phẩm rau củ tươi ngon";
        int quantity = 100;
        String brand = "Rau sạch Đà Lạt";
        String image = "vegetable.jpg"; // bạn có thể thay bằng ảnh thực tế

        return new Product(name, "Rau củ", price, description, quantity, brand, image);
    }
}