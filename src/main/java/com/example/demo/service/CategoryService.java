package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.JpaCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private JpaCategoryRepository repository;

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Optional<Category> saveCategory(Category category) {
        if (category.getId() != null && repository.existsById(category.getId())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(category));
    }

    public Optional<Category> getCategoryById(Long id) {
        return repository.findById(id);
    }

    public void removeCategoryById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Category> updateCategoryById(Long id, Category category) {
        if (repository.existsById(id)) {
            category.setId(id);
            return Optional.of(repository.save(category));
        }
        return Optional.empty();

    }


}
