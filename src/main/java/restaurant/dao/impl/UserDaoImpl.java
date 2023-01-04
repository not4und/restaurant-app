package restaurant.dao.impl;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.UserDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.User;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SELECT = "FROM User u JOIN FETCH u.roles ";

    public UserDaoImpl(SessionFactory factory) {
        super(factory, User.class);
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user with id: " + id, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("User with email '" + email + " not found.", e);
        }
    }
}
