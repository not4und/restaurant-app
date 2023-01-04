package restaurant.dao;

import java.util.List;
import java.util.Optional;
import restaurant.model.Category;
import restaurant.model.Product;

public interface ProductDao {
    Product add(Product product);

    Product update(Product product);

    Optional<Product> get(Long id);

    List<Product> getAll();

    List<Product> findByCategory(Category category);
}
