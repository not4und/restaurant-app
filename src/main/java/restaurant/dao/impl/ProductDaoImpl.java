package restaurant.dao.impl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.ProductDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.Category;
import restaurant.model.Product;

@Repository
public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao {
    private static final String SELECT = "FROM Product p JOIN FETCH p.category ";

    public ProductDaoImpl(SessionFactory factory) {
        super(factory, Product.class);
    }

    @Override
    public Optional<Product> get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE p.id = :id", Product.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get product with id: " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT, Product.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all products from DB.", e);
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT + "WHERE p.category = :category", Product.class)
                    .setParameter("category", category)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find product by category: " + category, e);
        }
    }
}
