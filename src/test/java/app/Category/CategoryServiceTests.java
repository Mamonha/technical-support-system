package app.Category;

import app.dto.category.ResponseCategory;
import app.entities.Category;
import app.repositories.CategoryRepository;
import app.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup(){
        Category category = new Category(1L, "Category 1", "Description 1", new ArrayList<>());
        List<Category> categories= new ArrayList<>();
        categories.add(new Category(1L, "Technology", "Technology related topics", new ArrayList<>()));
        categories.add(new Category(2L, "Health", "Health and wellness topics", new ArrayList<>()));
        categories.add(new Category(3L, "Education", "Educational topics and discussions", new ArrayList<>()));

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

    }

    @Test
    void storeTest () throws Exception {
        Category category = new Category();
        category.setNome("Category 1");
        category.setDescription( "Description 1");
        Category categoryResponse =categoryService.store(category);
    }

    @Test
    void index(){
        List<ResponseCategory> categories = categoryService.index();
        assertEquals(3, categories.size());
    }
}
