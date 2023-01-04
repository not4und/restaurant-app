package restaurant.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.ProductDao;
import restaurant.model.Category;
import restaurant.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private static final Long ID = 1L;
    private static final String NAME = "Product";
    private static final boolean DELETED = false;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductDao productDao;
    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Category");
        category.setId(ID);
        product = createProduct(ID, NAME, DELETED);
    }

    @Test
    void add_ok() {
        Product inputProduct = createProduct(null, NAME, DELETED);
        Mockito.when(productDao.add(inputProduct)).thenReturn(product);
        Assertions.assertEquals(product, productService.add(inputProduct));
    }

    @Test
    void update_ok() {
        product.setDeleted(!DELETED);
        Product inputProduct = createProduct(ID, NAME, !DELETED);
        Mockito.when(productDao.update(inputProduct)).thenReturn(product);
        Assertions.assertEquals(product, productService.update(inputProduct));
    }

    @Test
    void get_ok() {
        Mockito.when(productDao.get(ID)).thenReturn(Optional.of(product));
        Assertions.assertEquals(product, productService.get(ID));
    }

    @Test
    void get_notFound_notOk() {
        Mockito.when(productDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("Product with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> productService.get(ID)).getMessage());
    }

    @Test
    void getAll_ok() {
        List<Product> products = List.of(product, createProduct(ID + 1, NAME + "1", DELETED));
        Mockito.when(productDao.getAll()).thenReturn(products);
        Assertions.assertEquals(products, productService.getAll());
    }

    @Test
    void findByCategory_ok() {
        List<Product> products = List.of(product, createProduct(ID + 1, NAME + "1", DELETED));
        Mockito.when(productDao.findByCategory(category)).thenReturn(products);
        Assertions.assertEquals(products, productService.findByCategory(category));
    }

    private Product createProduct(Long id, String name, boolean deleted) {
        Product newProduct = new Product(name, BigDecimal.valueOf(150L), 99, deleted, category);
        newProduct.setId(id);
        return newProduct;
    }
}
