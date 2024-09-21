package app.controllers;

import app.dto.Ticket.ResponseTicket;
import app.dto.category.RequestCategory;
import app.dto.category.ResponseCategory;
import app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    protected CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody RequestCategory request) {
        try {
            categoryService.store(request.category());
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
        } catch (MethodArgumentNotValidException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + ex.getMessage());
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create category: " + err.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategory>> index() {
        try {
            List<ResponseCategory> tickets = categoryService.index();
            return ResponseEntity.ok(tickets);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
}
