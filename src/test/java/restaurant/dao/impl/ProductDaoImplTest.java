package restaurant.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.CategoryDao;
import restaurant.dao.ProductDao;
import restaurant.model.Category;
import restaurant.model.Product;

class ProductDaoImplTest extends AbstractTest {
    private ProductDao productDao;
    private Product product;
    private Category category;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Product.class, Category.class};
    }

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoImpl(getSessionFactory());
        CategoryDao categoryDao = new CategoryDaoImpl(getSessionFactory());
        category = categoryDao.add(new Category("Category"));
        product = new Product("Product", BigDecimal.valueOf(150L), 99, false, category);
    }

    @Test
    void add_ok() {
        Product actual = productDao.add(product);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(product, actual);
    }

    @Test
    void update_ok() {
        productDao.add(product);
        product.setName("Product1");
        Product actual = productDao.update(product);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(product, actual);
    }

    @Test
    void get_ok() {
        productDao.add(product);
        Optional<Product> actual = productDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(product, actual.get());
    }

    @Test
    void getAll_ok() {
        getProductListTest(addProducts(), productDao.getAll());
    }

    @Test
    void findByCategory_ok() {
        getProductListTest(addProducts(), productDao.findByCategory(category));
    }

    private Product addProducts() {
        productDao.add(product);
        return productDao.add(new Product("Product1", BigDecimal.valueOf(120L),
                66, false, category));
    }

    private void getProductListTest(Product product1, List<Product> actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.containsAll(List.of(product, product1)));
    }
}
