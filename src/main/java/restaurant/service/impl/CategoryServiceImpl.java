package restaurant.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import restaurant.dao.CategoryDao;
import restaurant.model.Category;
import restaurant.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Category add(Category category) {
        return categoryDao.add(category);
    }

    @Override
    public Category update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public Category get(Long id) {
        return categoryDao.get(id).orElseThrow(
                () -> new RuntimeException("Category with id: " + id + " not found.")
        );
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
