package strategy;

import java.util.List;
import java.util.stream.Collectors;
import models.Product;

public class SearchByName implements SearchStrategy {
    public List<Product> search(List<Product> products, String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
