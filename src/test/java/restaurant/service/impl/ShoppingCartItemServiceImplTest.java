package restaurant.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.model.Category;
import restaurant.model.Product;
import restaurant.model.ShoppingCartItem;

@ExtendWith(MockitoExtension.class)
class ShoppingCartItemServiceImplTest {
    private static final Long ID = 1L;
    private static final int AMOUNT = 1;
    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemService;
    @Mock
    private ShoppingCartItemDao shoppingCartItemDao;
    private ShoppingCartItem shoppingCartItem;
    private Product product;

    @BeforeEach
    void setUp() {
        Category category = new Category("Category");
        category.setId(ID);
        product = new Product("Product", BigDecimal.valueOf(150L), 99, false, category);
        product.setId(ID);
        shoppingCartItem = createShoppingCartItem(ID, AMOUNT);
    }

    @Test
    void add_ok() {
        ShoppingCartItem inputShoppingCartItem = createShoppingCartItem(null, AMOUNT);
        Mockito.when(shoppingCartItemDao.add(inputShoppingCartItem)).thenReturn(shoppingCartItem);
        Assertions.assertEquals(shoppingCartItem,
                shoppingCartItemService.add(inputShoppingCartItem));
    }

    @Test
    void update_ok() {
        shoppingCartItem.setAmount(AMOUNT + 1);
        ShoppingCartItem inputShoppingCartItem = createShoppingCartItem(ID + 1, AMOUNT + 1);
        Mockito.when(shoppingCartItemDao.update(inputShoppingCartItem))
                .thenReturn(shoppingCartItem);
        Assertions.assertEquals(shoppingCartItem,
                shoppingCartItemService.update(inputShoppingCartItem));
    }

    @Test
    void get_ok() {
        Mockito.when(shoppingCartItemDao.get(ID)).thenReturn(Optional.of(shoppingCartItem));
        Assertions.assertEquals(shoppingCartItem, shoppingCartItemService.get(ID));
    }

    @Test
    void get_notFound_notOk() {
        Mockito.when(shoppingCartItemDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("Shopping cart item with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> shoppingCartItemService.get(ID)).getMessage());
    }

    @Test
    void delete_ok() {
        Mockito.when(shoppingCartItemDao.delete(ID)).thenReturn(true);
        Assertions.assertTrue(shoppingCartItemService.delete(ID));
    }

    private ShoppingCartItem createShoppingCartItem(Long id, int amount) {
        ShoppingCartItem newShoppingCartItem = new ShoppingCartItem(product, amount);
        newShoppingCartItem.setId(id);
        return newShoppingCartItem;
    }
}
