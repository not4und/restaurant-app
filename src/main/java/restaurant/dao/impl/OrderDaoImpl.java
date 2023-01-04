package restaurant.dao.impl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.OrderDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.Order;
import restaurant.model.Table;
import restaurant.model.User;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final String SELECT = "FROM Order o "
            + "JOIN FETCH o.product p "
            + "JOIN FETCH p.category "
            + "JOIN FETCH o.table t "
            + "JOIN FETCH o.user u "
            + "JOIN FETCH u.roles r ";

    public OrderDaoImpl(SessionFactory factory) {
        super(factory, Order.class);
    }

    @Override
    public Optional<Order> get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE o.id = :id", Order.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get order with id: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE o.done IS FALSE", Order.class)
                .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all "
                     + "Orders from db.", e);
        }
    }

    @Override
    public List<Order> getAllByTable(Table table) {
        return getAllBy(table);
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return getAllBy(user);
    }

    private List<Order> getAllBy(Object model) {
        String modelName = model.getClass().getSimpleName();
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT
                            + "WHERE o." + modelName.toLowerCase() + " = :model "
                            + "AND o.done IS FALSE", Order.class)
                    .setParameter("model", model)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all orders by "
                    + modelName + ": " + model, e);
        }
    }
}
