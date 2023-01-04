package restaurant.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.CategoryDao;
import restaurant.dao.ProductDao;
import restaurant.dao.RoleDao;
import restaurant.dao.ShoppingCartDao;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.dao.UserDao;
import restaurant.model.Category;
import restaurant.model.Product;
import restaurant.model.Role;
import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.model.User;

class ShoppingCartDaoImplTest extends AbstractTest {
    private ShoppingCartDao shoppingCartDao;
    private ShoppingCart shoppingCart;
    private User user;
    private Product product;
    private ShoppingCartItem shoppingCartItem;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {ShoppingCart.class, User.class, Role.class,
                ShoppingCartItem.class, Product.class, Category.class};
    }

    @BeforeEach
    void setUp() {
        shoppingCartDao = new ShoppingCartDaoImpl(getSessionFactory());
        UserDao userDao = new UserDaoImpl(getSessionFactory());
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        CategoryDao categoryDao = new CategoryDaoImpl(getSessionFactory());
        ProductDao productDao = new ProductDaoImpl(getSessionFactory());
        ShoppingCartItemDao shoppingCartItemDao = new ShoppingCartItemDaoImpl(getSessionFactory());
        user = userDao.add(new User("user@e.mail", "1q2w3e4r",
                Set.of(roleDao.add(new Role(Role.RoleName.USER)))));
        product = productDao.add(new Product("Product", BigDecimal.valueOf(150L),
                99, false, categoryDao.add(new Category("Category"))));
        shoppingCartItem = shoppingCartItemDao.add(new ShoppingCartItem(product, 1));
        shoppingCart = new ShoppingCart(user, new ArrayList<>(List.of(shoppingCartItem)));
    }

    @Test
    void add_ok() {
        ShoppingCart actual = shoppingCartDao.add(shoppingCart);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(shoppingCart, actual);
    }

    @Test
    void update_ok() {
        shoppingCartDao.add(shoppingCart);
        List<ShoppingCartItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
        shoppingCartItems.remove(shoppingCartItem);
        shoppingCart.setShoppingCartItems(shoppingCartItems);
        ShoppingCart actual = shoppingCartDao.update(shoppingCart);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(shoppingCart, actual);
    }

    @Test
    void getByUser_ok() {
        shoppingCartDao.add(shoppingCart);
        ShoppingCart actual = shoppingCartDao.getByUser(user);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(shoppingCart.getUser(), actual.getUser());
    }
}
