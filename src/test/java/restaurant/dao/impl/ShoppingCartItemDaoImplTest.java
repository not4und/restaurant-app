package restaurant.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.CategoryDao;
import restaurant.dao.ProductDao;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.model.Category;
import restaurant.model.Product;
import restaurant.model.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.Optional;

class ShoppingCartItemDaoImplTest extends AbstractTest {
    private ShoppingCartItemDao shoppingCartItemDao;
    private ShoppingCartItem shoppingCartItem;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {ShoppingCartItem.class, Product.class, Category.class};
    }

    @BeforeEach
    void setUp() {
        shoppingCartItemDao = new ShoppingCartItemDaoImpl(getSessionFactory());
        ProductDao productDao = new ProductDaoImpl(getSessionFactory());
        CategoryDao categoryDao = new CategoryDaoImpl(getSessionFactory());
        Product product = productDao.add(new Product("Product", BigDecimal.valueOf(150L),
                99, false, categoryDao.add(new Category("Category"))));
        shoppingCartItem = new ShoppingCartItem(product, 1);
    }

    @Test
    void add_ok() {
        ShoppingCartItem actual = shoppingCartItemDao.add(shoppingCartItem);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(shoppingCartItem, actual);
    }

    @Test
    void update_ok() {
        shoppingCartItemDao.add(shoppingCartItem);
        shoppingCartItem.setAmount(2);
        ShoppingCartItem actual = shoppingCartItemDao.update(shoppingCartItem);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(shoppingCartItem, actual);
    }

    @Test
    void get_ok() {
        shoppingCartItemDao.add(shoppingCartItem);
        Optional<ShoppingCartItem> actual = shoppingCartItemDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(shoppingCartItem, actual.get());
    }

    @Test
    void delete_ok() {
        shoppingCartItemDao.add(shoppingCartItem);
        Assertions.assertTrue(shoppingCartItemDao.delete(1L));
    }
}
