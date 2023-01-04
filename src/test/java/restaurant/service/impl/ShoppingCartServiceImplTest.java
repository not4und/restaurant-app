package restaurant.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.ShoppingCartDao;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.model.Category;
import restaurant.model.Product;
import restaurant.model.Role;
import restaurant.model.ShoppingCart;
import restaurant.model.ShoppingCartItem;
import restaurant.model.User;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static final Long ID = 1L;
    private static final int AMOUNT = 1;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartDao shoppingCartDao;
    @Mock
    private ShoppingCartItemDao shoppingCartItemDao;
    private ShoppingCart shoppingCart;
    private ShoppingCartItem shoppingCartItem;
    private Product product;
    private User user;

    @BeforeEach
    void setUp() {
        Role role = new Role(Role.RoleName.USER);
        role.setId(ID);
        user = new User("user@e.mail", "1q2w3e4r", Set.of(role));
        user.setId(ID);
        shoppingCart = createShoppingCart(ID, new ArrayList<>());
        Category category = new Category("Category");
        category.setId(ID);
        product = new Product("Product", BigDecimal.valueOf(150L), 99, false, category);
        product.setId(ID);
        shoppingCartItem = createShoppingCartItem(ID, AMOUNT);
    }

    @Test
    void registerNewShoppingCart_ok() {
        Mockito.when(shoppingCartDao.add(ArgumentMatchers.any())).thenReturn(shoppingCart);
        Assertions.assertEquals(shoppingCart, shoppingCartService.registerNewShoppingCart(user));
    }

    @Test
    void getByUser_ok() {
        Mockito.when(shoppingCartDao.getByUser(user)).thenReturn(shoppingCart);
        Assertions.assertEquals(shoppingCart, shoppingCartService.getByUser(user));
    }

    @Test
    void update_ok() {
        shoppingCart.getShoppingCartItems().add(shoppingCartItem);
        ShoppingCart inputShoppingCart = createShoppingCart(ID, List.of(shoppingCartItem));
        Mockito.when(shoppingCartDao.update(inputShoppingCart)).thenReturn(shoppingCart);
        Assertions.assertEquals(shoppingCart, shoppingCartService.update(inputShoppingCart));
    }

    @Test
    void addShoppingCartItem_ok() {
        ShoppingCartItem inputShoppingCartItem = createShoppingCartItem(null, 1);
        Mockito.when(shoppingCartDao.getByUser(user)).thenReturn(shoppingCart);
        shoppingCart.getShoppingCartItems().add(shoppingCartItem);
        Mockito.when(shoppingCartItemDao.add(inputShoppingCartItem)).thenReturn(shoppingCartItem);
        Mockito.when(shoppingCartDao.update(shoppingCart)).thenReturn(shoppingCart);
        Assertions.assertEquals(shoppingCart,
                shoppingCartService.addShoppingCartItem(inputShoppingCartItem, user));
    }

    private ShoppingCart createShoppingCart(Long id, List<ShoppingCartItem> items) {
        ShoppingCart newShoppingCart = new ShoppingCart(user, items);
        newShoppingCart.setId(id);
        return newShoppingCart;
    }

    private ShoppingCartItem createShoppingCartItem(Long id, int amount) {
        ShoppingCartItem newShoppingCartItem = new ShoppingCartItem(product, amount);
        newShoppingCartItem.setId(id);
        return newShoppingCartItem;
    }
}
