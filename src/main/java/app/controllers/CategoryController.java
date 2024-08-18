package app.controllers;

import app.dto.category.RequestCategory;
import app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    protected CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody RequestCategory request){
        try{
            categoryService.store(request.category());
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create category: " + err.getMessage());
        }
    }
}
