package restaurant.service;

import java.util.List;
import restaurant.model.Category;

public interface CategoryService {
    Category add(Category category);

    Category update(Category category);

    Category get(Long id);

    List<Category> getAll();
}
