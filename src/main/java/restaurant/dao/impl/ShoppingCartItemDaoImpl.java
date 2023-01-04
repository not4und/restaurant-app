package restaurant.dao.impl;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.ShoppingCartItemDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.ShoppingCartItem;

@Repository
public class ShoppingCartItemDaoImpl
        extends AbstractDao<ShoppingCartItem>
        implements ShoppingCartItemDao {
    public ShoppingCartItemDaoImpl(SessionFactory factory) {
        super(factory, ShoppingCartItem.class);
    }

    @Override
    public Optional<ShoppingCartItem> get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM ShoppingCartItem sci "
                            + "JOIN FETCH sci.product "
                            + "WHERE sci.id = :id", ShoppingCartItem.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shopping cart item with id: " + id, e);
        }
    }
}
