package strategy;

import java.util.List;
import models.Product;

public interface SearchStrategy {
    List<Product> search(List<Product> products, String keyword);
}
