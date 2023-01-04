package restaurant.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.CategoryDao;
import restaurant.dao.OrderDao;
import restaurant.dao.ProductDao;
import restaurant.dao.RoleDao;
import restaurant.dao.TableDao;
import restaurant.dao.UserDao;
import restaurant.model.Category;
import restaurant.model.Order;
import restaurant.model.Product;
import restaurant.model.Role;
import restaurant.model.Table;
import restaurant.model.User;

class OrderDaoImplTest extends AbstractTest {
    private OrderDao orderDao;
    private Order order;
    private Product product;
    private Table table;
    private User user;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Order.class, Product.class, Category.class,
                Table.class, User.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        CategoryDao categoryDao = new CategoryDaoImpl(getSessionFactory());
        ProductDao productDao = new ProductDaoImpl(getSessionFactory());
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        UserDao userDao = new UserDaoImpl(getSessionFactory());
        TableDao tableDao = new TableDaoImpl(getSessionFactory());
        orderDao = new OrderDaoImpl(getSessionFactory());
        Category category = categoryDao.add(new Category("Category"));
        product = productDao.add(new Product("Product", BigDecimal.valueOf(150L),
                99, false, category));
        Role role = roleDao.add(new Role(Role.RoleName.USER));
        roleDao.add(new Role(Role.RoleName.ADMIN));
        user = userDao.add(new User("user@e.mail", "1q2w3e4r", Set.of(role)));
        table = tableDao.add(new Table(6, false));
        order = new Order(product, 1, false,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), table, user);
    }

    @Test
    void add_ok() {
        Order actual = orderDao.add(order);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(order, actual);
    }

    @Test
    void update_ok() {
        orderDao.add(order);
        order.setDone(true);
        Order actual = orderDao.update(order);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(order, actual);
    }

    @Test
    void get_ok() {
        orderDao.add(order);
        Optional<Order> actual = orderDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(order, actual.get());
    }

    @Test
    void getAll_ok() {
        getOrderListTest(addOrders(), orderDao.getAll());
    }

    @Test
    void getAllByTable_ok() {
        getOrderListTest(addOrders(), orderDao.getAllByTable(table));
    }

    @Test
    void getAllByUser_ok() {
        getOrderListTest(addOrders(), orderDao.getAllByUser(user));
    }

    private Order addOrders() {
        orderDao.add(order);
        return orderDao.add(new Order(product, 2, false,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), table, user));
    }

    private void getOrderListTest(Order order1, List<Order> actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.containsAll(List.of(order, order1)));
    }
}
