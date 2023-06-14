package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Client;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        final Optional<Category> category = service.getCategoryById(id);
        if (category.isPresent()) {
            log.info("getting category with id {}", id);
            return ResponseEntity.ok().body(category.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.getCategoryById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("deleting category with id {}", id);
        service.removeCategoryById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Category> post(@RequestBody Category requestCategory) {
        Optional<Category> savedCategory = service.saveCategory(requestCategory);
        if (savedCategory.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedCategory.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> updatedProduct = service.updateCategoryById(id, category);
        if (!updatedProduct.isEmpty()) {
            return ResponseEntity
                    .ok(updatedProduct.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }
}
