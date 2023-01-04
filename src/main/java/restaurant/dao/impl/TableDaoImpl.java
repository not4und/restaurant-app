package restaurant.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.TableDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.Table;

@Repository
public class TableDaoImpl extends AbstractDao<Table> implements TableDao {
    public TableDaoImpl(SessionFactory factory) {
        super(factory, Table.class);
    }

    @Override
    public List<Table> getAllByReservation(boolean reserved) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Table WHERE reserved = :reserved", Table.class)
                    .setParameter("reserved", reserved)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all tables by reservation.", e);
        }
    }
}
