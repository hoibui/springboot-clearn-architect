package com.example.demo.athena.application.service;

import com.example.demo.athena.domain.model.Category;
import com.example.demo.athena.infrastructure.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryJpaRepository categoryJpaRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryJpaRepository);
    }

    @Test
    @DisplayName("Should return all categories when getAllCategories is called")
    void getAllCategories_shouldReturnAllCategories_whenCalled() {
        // Given
        List<Category> expectedCategories = Arrays.asList(
            createCategory(1L, "Electronics"),
            createCategory(2L, "Books"),
            createCategory(3L, "Clothing")
        );
        
        when(categoryJpaRepository.findAll()).thenReturn(expectedCategories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyElementsOf(expectedCategories);
        
        verify(categoryJpaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return category when valid ID exists")
    void getCategoryById_shouldReturnCategory_whenValidIdExists() {
        // Given
        Long categoryId = 1L;
        Category expectedCategory = createCategory(categoryId, "Electronics");
        
        when(categoryJpaRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedCategory);
        assertThat(result.get().getId()).isEqualTo(categoryId);
        assertThat(result.get().getName()).isEqualTo("Electronics");
        
        verify(categoryJpaRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should return empty Optional when category ID does not exist")
    void getCategoryById_shouldReturnEmpty_whenCategoryIdDoesNotExist() {
        // Given
        Long categoryId = 999L;
        
        when(categoryJpaRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isEmpty();
        
        verify(categoryJpaRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should create category successfully when valid data is provided")
    void createCategory_shouldReturnCategory_whenValidDataProvided() {
        // Given
        Category inputCategory = new Category("Electronics");
        Category expectedCategory = createCategory(1L, "Electronics");
        
        when(categoryJpaRepository.save(any(Category.class))).thenReturn(expectedCategory);

        // When
        Category result = categoryService.createCategory(inputCategory);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Electronics");
        
        verify(categoryJpaRepository, times(1)).save(inputCategory);
    }

    @Test
    @DisplayName("Should delete category when valid ID is provided")
    void deleteCategory_shouldCallDeleteById_whenValidIdProvided() {
        // Given
        Long categoryId = 1L;
        
        doNothing().when(categoryJpaRepository).deleteById(categoryId);

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryJpaRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Should handle category with null name")
    void createCategory_shouldHandleNullName_whenNullNameProvided() {
        // Given
        Category inputCategory = new Category();
        inputCategory.setName(null);
        
        Category expectedCategory = createCategory(1L, null);
        
        when(categoryJpaRepository.save(any(Category.class))).thenReturn(expectedCategory);

        // When
        Category result = categoryService.createCategory(inputCategory);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isNull();
        
        verify(categoryJpaRepository, times(1)).save(inputCategory);
    }

    @Test
    @DisplayName("Should handle category with empty name")
    void createCategory_shouldHandleEmptyName_whenEmptyNameProvided() {
        // Given
        Category inputCategory = new Category("");
        Category expectedCategory = createCategory(1L, "");
        
        when(categoryJpaRepository.save(any(Category.class))).thenReturn(expectedCategory);

        // When
        Category result = categoryService.createCategory(inputCategory);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("");
        
        verify(categoryJpaRepository, times(1)).save(inputCategory);
    }

    @Test
    @DisplayName("Should return empty list when no categories exist")
    void getAllCategories_shouldReturnEmptyList_whenNoCategoriesExist() {
        // Given
        when(categoryJpaRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        
        verify(categoryJpaRepository, times(1)).findAll();
    }

    private Category createCategory(Long id, String name) {
        Category category = new Category(name);
        category.setId(id);
        return category;
    }
}