package app.services;

import app.dto.Ticket.ResponseTicket;
import app.dto.category.ResponseCategory;
import app.entities.Category;
import app.entities.User;
import app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ResponseCategory> index(){
        List <Category> categories= categoryRepository.findAll();
        return categories.stream()
                .map(ResponseCategory::category)
                .collect(Collectors.toList());
    }
}