package app.Category;

import app.controllers.CategoryController;
import app.dto.category.RequestCategory;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryControllerTest {
    @Autowired
    CategoryController categoryController;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup (){
        Category category = new Category(1L, "Category 1", "Description 1", new ArrayList<>());
        List<Category> categories= new ArrayList<>();
        categories.add(new Category(1L, "Technology", "Technology related topics", new ArrayList<>()));
        categories.add(new Category(2L, "Health", "Health and wellness topics", new ArrayList<>()));
        categories.add(new Category(3L, "Education", "Educational topics and discussions", new ArrayList<>()));
        List <ResponseCategory> responseCategories= new ArrayList<>();
        for (Category category1: categories){
           responseCategories.add(ResponseCategory.category(category1));
        }
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        Mockito.when(categoryService.index()).thenReturn(responseCategories);
    }

    @Test
    void testStoreCategory() {
        RequestCategory request = new RequestCategory();
        request.setName("Technology");
        request.setDescription("Technology related topics");

        ResponseEntity<String> response = categoryController.store(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category created successfully.", response.getBody());
    }

    @Test
    void testIndexCategories() {
        ResponseEntity<List<ResponseCategory>> response = categoryController.index();
        List<ResponseCategory> responseCategories=  response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, responseCategories.size());
        assertEquals("Technology", responseCategories.get(0).getName());
    }


    @Test
    void testStoreGeneralException() throws Exception {
        RequestCategory request = new RequestCategory();
        request.setName("Technology");
        request.setDescription("Technology related topics");

        Mockito.doThrow(new RuntimeException("General error"))
                .when(categoryService).store(Mockito.any());

        ResponseEntity<String> response = categoryController.store(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create category: General error", response.getBody());
    }

    @Test
    void testIndexGeneralException() {
        Mockito.doThrow(new RuntimeException("General error"))
                .when(categoryService).index();

        ResponseEntity<List<ResponseCategory>> response = categoryController.index();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
