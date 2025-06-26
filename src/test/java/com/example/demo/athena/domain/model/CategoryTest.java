package com.example.demo.athena.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("Should create category with default constructor")
    void constructor_shouldCreateCategory_whenDefaultConstructorUsed() {
        // When
        Category category = new Category();

        // Then
        assertThat(category).isNotNull();
        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isNull();
        assertThat(category.getProducts()).isNull();
    }

    @Test
    @DisplayName("Should create category with name constructor")
    void constructor_shouldCreateCategory_whenNameConstructorUsed() {
        // Given
        String name = "Electronics";

        // When
        Category category = new Category(name);

        // Then
        assertThat(category).isNotNull();
        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isEqualTo(name);
        assertThat(category.getProducts()).isNull();
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void setId_shouldSetId_whenValidIdProvided() {
        // Given
        Category category = new Category();
        Long id = 1L;

        // When
        category.setId(id);

        // Then
        assertThat(category.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should set and get name correctly")
    void setName_shouldSetName_whenValidNameProvided() {
        // Given
        Category category = new Category();
        String name = "Books";

        // When
        category.setName(name);

        // Then
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should handle null name")
    void setName_shouldHandleNullName_whenNullNameProvided() {
        // Given
        Category category = new Category();

        // When
        category.setName(null);

        // Then
        assertThat(category.getName()).isNull();
    }

    @Test
    @DisplayName("Should handle empty name")
    void setName_shouldHandleEmptyName_whenEmptyNameProvided() {
        // Given
        Category category = new Category();
        String emptyName = "";

        // When
        category.setName(emptyName);

        // Then
        assertThat(category.getName()).isEqualTo(emptyName);
    }

    @Test
    @DisplayName("Should set and get products list correctly")
    void setProducts_shouldSetProducts_whenValidProductsListProvided() {
        // Given
        Category category = new Category("Electronics");
        List<Product> products = new ArrayList<>();
        
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setCategory(category);
        
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Phone");
        product2.setCategory(category);
        
        products.add(product1);
        products.add(product2);

        // When
        category.setProducts(products);

        // Then
        assertThat(category.getProducts()).isNotNull();
        assertThat(category.getProducts()).hasSize(2);
        assertThat(category.getProducts()).containsExactlyElementsOf(products);
    }

    @Test
    @DisplayName("Should handle empty products list")
    void setProducts_shouldHandleEmptyList_whenEmptyProductsListProvided() {
        // Given
        Category category = new Category("Electronics");
        List<Product> emptyProducts = new ArrayList<>();

        // When
        category.setProducts(emptyProducts);

        // Then
        assertThat(category.getProducts()).isNotNull();
        assertThat(category.getProducts()).isEmpty();
    }

    @Test
    @DisplayName("Should handle null products list")
    void setProducts_shouldHandleNullList_whenNullProductsListProvided() {
        // Given
        Category category = new Category("Electronics");

        // When
        category.setProducts(null);

        // Then
        assertThat(category.getProducts()).isNull();
    }

    @Test
    @DisplayName("Should maintain bidirectional relationship with products")
    void relationship_shouldMaintainBidirectionalRelationship_whenProductsSet() {
        // Given
        Category category = new Category("Electronics");
        category.setId(1L);
        
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setCategory(category);
        
        List<Product> products = Arrays.asList(product);

        // When
        category.setProducts(products);

        // Then
        assertThat(category.getProducts()).hasSize(1);
        assertThat(category.getProducts().get(0)).isEqualTo(product);
        assertThat(category.getProducts().get(0).getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("Should create category with complete data")
    void fullObject_shouldCreateCompleteCategory_whenAllDataProvided() {
        // Given
        Long id = 1L;
        String name = "Electronics";
        
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Phone");
        
        List<Product> products = Arrays.asList(product1, product2);

        // When
        Category category = new Category(name);
        category.setId(id);
        category.setProducts(products);

        // Then
        assertThat(category.getId()).isEqualTo(id);
        assertThat(category.getName()).isEqualTo(name);
        assertThat(category.getProducts()).hasSize(2);
        assertThat(category.getProducts()).containsExactlyElementsOf(products);
    }
}