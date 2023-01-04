package restaurant.service;

import java.util.List;
import restaurant.model.Category;
import restaurant.model.Product;

public interface ProductService {
    Product add(Product product);

    Product update(Product product);

    Product get(Long id);

    List<Product> getAll();

    List<Product> findByCategory(Category category);
}
