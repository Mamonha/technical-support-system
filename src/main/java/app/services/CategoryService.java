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

    public Category store(Category category) throws Exception {
        if (category.getNome() == null || category.getNome().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        try {
           return categoryRepository.save(category);
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