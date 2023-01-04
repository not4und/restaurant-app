package restaurant.service;

import java.util.List;
import restaurant.model.Order;
import restaurant.model.Table;
import restaurant.model.User;

public interface OrderService {
    Order add(Order order);

    Order update(Order order);

    Order get(Long id);

    List<Order> getAll();

    List<Order> getAllByTable(Table table);

    List<Order> getAllByUser(User user);
}
