package restaurant.service.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.CategoryDao;
import restaurant.model.Category;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final Long ID = 1L;
    private static final String NAME = "Category";
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryDao categoryDao;
    private Category category;

    @BeforeEach
    void setUp() {
        category = createCategory(ID, NAME);
    }

    @Test
    void add_ok() {
        Category inputCategory = createCategory(null, NAME);
        Mockito.when(categoryDao.add(inputCategory)).thenReturn(category);
        Assertions.assertEquals(category, categoryService.add(inputCategory));
    }

    @Test
    void update_ok() {
        category.setName(NAME + "1");
        Category inputCategory = createCategory(ID, NAME + "1");
        Mockito.when(categoryDao.update(inputCategory)).thenReturn(category);
        Assertions.assertEquals(category, categoryService.update(inputCategory));
    }

    @Test
    void get_ok() {
        Mockito.when(categoryDao.get(ID)).thenReturn(Optional.of(category));
        Assertions.assertEquals(category, categoryService.get(ID));
    }

    @Test
    void get_notFound_not_ok() {
        Mockito.when(categoryDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("Category with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> categoryService.get(ID)).getMessage());
    }

    @Test
    void getAll_ok() {
        List<Category> categories = List.of(category, createCategory(ID + 1, NAME + "1"));
        Mockito.when(categoryDao.getAll()).thenReturn(categories);
        Assertions.assertEquals(categories, categoryService.getAll());
    }

    private Category createCategory(Long id, String name) {
        Category newCategory = new Category(name);
        newCategory.setId(id);
        return newCategory;
    }
}
