package restaurant.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import restaurant.dao.OrderDao;
import restaurant.model.Order;
import restaurant.model.Table;
import restaurant.model.User;
import restaurant.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order add(Order order) {
        return orderDao.add(order);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).orElseThrow(
                () -> new RuntimeException("Order with id: " + id + " not found.")
        );
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public List<Order> getAllByTable(Table table) {
        return orderDao.getAllByTable(table);
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return orderDao.getAllByUser(user);
    }
}
