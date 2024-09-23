package app.factory;

import app.entities.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryFactory {
    public static Category createDefaultCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setNome("Test category");
        category.setDescription("Test description");
        category.setList(new ArrayList<>());

        return category;
    }

    public static Category createCustomCategory(Long id, String nome, String description) {
        Category category = new Category();
        category.setId(id);
        category.setNome(nome);
        category.setDescription(description);
        category.setList(new ArrayList<>());

        return category;
    }
}
