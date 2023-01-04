package restaurant.dao;

import java.util.List;
import java.util.Optional;
import restaurant.model.Category;

public interface CategoryDao {
    Category add(Category category);

    Category update(Category category);

    Optional<Category> get(Long id);

    List<Category> getAll();
}
