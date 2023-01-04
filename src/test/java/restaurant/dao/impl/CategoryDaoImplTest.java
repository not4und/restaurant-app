package restaurant.dao.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.CategoryDao;
import restaurant.exception.DataProcessingException;
import restaurant.model.Category;

public class CategoryDaoImplTest extends AbstractTest {
    private CategoryDao categoryDao;
    private Category category;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Category.class};
    }

    @BeforeEach
    void setUp() {
        categoryDao = new CategoryDaoImpl(getSessionFactory());
        category = new Category("Category");
    }

    @Test
    void add_ok() {
        Category actual = categoryDao.add(category);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(category, actual);
    }

    @Test
    void add_notUniqueName_notOk() {
        categoryDao.add(category);
        Assertions.assertEquals("Can't insert "
                + category.getClass().getSimpleName() + ": " + category,
                Assertions.assertThrows(DataProcessingException.class,
                        () -> categoryDao.add(category)).getMessage());
    }

    @Test
    void update_ok() {
        categoryDao.add(category);
        category.setName("CategoryUpdated");
        Category actual = categoryDao.update(category);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(category, actual);
    }

    @Test
    void get_ok() {
        categoryDao.add(category);
        Optional<Category> actual = categoryDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(category, actual.get());
    }

    @Test
    void getAll_ok() {
        categoryDao.add(category);
        Category category1 = categoryDao.add(new Category("Category1"));
        List<Category> actual = categoryDao.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.containsAll(List.of(category, category1)));
    }
}
