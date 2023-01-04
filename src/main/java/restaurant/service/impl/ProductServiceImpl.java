package restaurant.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import restaurant.dao.ProductDao;
import restaurant.model.Category;
import restaurant.model.Product;
import restaurant.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product add(Product product) {
        return productDao.add(product);
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id).orElseThrow(
                () -> new RuntimeException("Product with id: " + id + " not found.")
        );
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productDao.findByCategory(category);
    }
}
