package restaurant.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import restaurant.dao.CategoryDao;
import restaurant.model.Category;

@Repository
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
    public CategoryDaoImpl(SessionFactory factory) {
        super(factory, Category.class);
    }
}
