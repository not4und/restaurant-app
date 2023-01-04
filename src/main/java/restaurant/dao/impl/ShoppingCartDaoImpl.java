package restaurant.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.ShoppingCartDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.ShoppingCart;
import restaurant.model.User;

@Repository
public class ShoppingCartDaoImpl extends AbstractDao<ShoppingCart> implements ShoppingCartDao {
    public ShoppingCartDaoImpl(SessionFactory factory) {
        super(factory, ShoppingCart.class);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT DISTINCT sc FROM ShoppingCart sc "
                    + "LEFT JOIN FETCH sc.shoppingCartItems sci "
                    + "LEFT JOIN FETCH sci.product p "
                    + "LEFT JOIN FETCH p.category "
                    + "LEFT JOIN FETCH sc.user u "
                    + "LEFT JOIN FETCH u.roles "
                    + "WHERE u = :user", ShoppingCart.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shopping cart by user: " + user, e);
        }
    }
}
