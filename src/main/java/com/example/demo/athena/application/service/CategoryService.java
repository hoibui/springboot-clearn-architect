package com.example.demo.athena.application.service;

import com.example.demo.athena.domain.model.Category;
import com.example.demo.athena.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryService(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    public List<Category> getAllCategories() {
        return categoryJpaRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryJpaRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryJpaRepository.deleteById(id);
    }
}
