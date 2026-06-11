package strategy;

import java.util.List;
import models.Product;

public class ProductSearcher {
    private SearchStrategy strategy;
    public void setStrategy(SearchStrategy s) { strategy = s; }
    public List<Product> search(List<Product> products, String keyword, String category, Double maxPrice) {
        return strategy.search(products, keyword);
    }
}