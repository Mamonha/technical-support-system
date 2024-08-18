package app.services;

import app.entities.Category;
import app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    protected CategoryRepository categoryRepository;
    
    public void store(Category category) throws Exception {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new Exception("Failed to create category: " + e.getMessage());
        }
    }
}