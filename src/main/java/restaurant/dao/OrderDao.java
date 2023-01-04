package restaurant.dao;

import java.util.List;
import java.util.Optional;
import restaurant.model.Order;
import restaurant.model.Table;
import restaurant.model.User;

public interface OrderDao {
    Order add(Order order);

    Order update(Order order);

    Optional<Order> get(Long id);

    List<Order> getAll();

    List<Order> getAllByTable(Table table);

    List<Order> getAllByUser(User user);
}
