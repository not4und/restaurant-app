package restaurant.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.OrderDao;
import restaurant.model.Category;
import restaurant.model.Order;
import restaurant.model.Product;
import restaurant.model.Role;
import restaurant.model.Table;
import restaurant.model.User;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final Long ID = 1L;
    private static final boolean DONE = false;
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderDao orderDao;
    private Order order;
    private Product product;
    private Table table;
    private User user;

    @BeforeEach
    void setUp() {
        Category category = new Category("Category");
        category.setId(ID);
        product = new Product("Product", BigDecimal.valueOf(150L), 99, false, category);
        product.setId(ID);
        table = new Table(6, false);
        table.setId(ID);
        user = new User("user@e.mail", "1q2w3e4r", Set.of(new Role(Role.RoleName.USER)));
        user.setId(ID);
        order = createOrder(ID, DONE);
    }

    @Test
    void add_ok() {
        Order inputOrder = createOrder(null, DONE);
        Mockito.when(orderDao.add(inputOrder)).thenReturn(order);
        Assertions.assertEquals(order, orderService.add(inputOrder));
    }

    @Test
    void update_ok() {
        order.setDone(!DONE);
        Order inputOrder = createOrder(ID, !DONE);
        Mockito.when(orderDao.update(inputOrder)).thenReturn(order);
        Assertions.assertEquals(order, orderService.update(inputOrder));
    }

    @Test
    void get_ok() {
        Mockito.when(orderDao.get(ID)).thenReturn(Optional.of(order));
        Assertions.assertEquals(order, orderService.get(ID));
    }

    @Test
    void get_notFound_notOk() {
        Mockito.when(orderDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("Order with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> orderService.get(ID)).getMessage());

    }

    @Test
    void getAll_ok() {
        List<Order> orders = List.of(order, createOrder(ID + 1, DONE));
        Mockito.when(orderDao.getAll()).thenReturn(orders);
        Assertions.assertEquals(orders, orderService.getAll());
    }

    @Test
    void getAllByTable_ok() {
        List<Order> orders = List.of(order, createOrder(ID + 1, DONE));
        Mockito.when(orderDao.getAllByTable(table)).thenReturn(orders);
        Assertions.assertEquals(orders, orderService.getAllByTable(table));
    }

    @Test
    void getAllByUser_ok() {
        List<Order> orders = List.of(order, createOrder(ID + 1, DONE));
        Mockito.when(orderDao.getAllByUser(user)).thenReturn(orders);
        Assertions.assertEquals(orders, orderService.getAllByUser(user));
    }

    private Order createOrder(Long id, boolean done) {
        Order newOrder = new Order(product, 1, done,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), table, user);
        newOrder.setId(id);
        return newOrder;
    }
}
