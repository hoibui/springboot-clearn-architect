package com.example.demo.athena.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    @DisplayName("Should create product with default constructor")
    void constructor_shouldCreateProduct_whenDefaultConstructorUsed() {
        // When
        Product product = new Product();

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNull();
        assertThat(product.getName()).isNull();
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(product.getCategory()).isNull();
    }

    @Test
    @DisplayName("Should create product with all args constructor")
    void constructor_shouldCreateProduct_whenAllArgsConstructorUsed() {
        // Given
        Long id = 1L;
        String name = "Laptop";
        int quantity = 10;
        Category category = new Category("Electronics");

        // When
        Product product = new Product(id, name, quantity, category);

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getQuantity()).isEqualTo(quantity);
        assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("Should set and get properties correctly")
    void properties_shouldSetAndGet_whenValidValuesProvided() {
        // Given
        Product product = new Product();
        Long id = 1L;
        String name = "Smartphone";
        int quantity = 5;
        Category category = new Category("Electronics");

        // When
        product.setId(id);
        product.setName(name);
        product.setQuantity(quantity);
        product.setCategory(category);

        // Then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getQuantity()).isEqualTo(quantity);
        assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("Should handle null name")
    void properties_shouldHandleNullName_whenNullNameSet() {
        // Given
        Product product = new Product();

        // When
        product.setName(null);

        // Then
        assertThat(product.getName()).isNull();
    }

    @Test
    @DisplayName("Should handle zero quantity")
    void properties_shouldHandleZeroQuantity_whenZeroQuantitySet() {
        // Given
        Product product = new Product();

        // When
        product.setQuantity(0);

        // Then
        assertThat(product.getQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should handle negative quantity")
    void properties_shouldHandleNegativeQuantity_whenNegativeQuantitySet() {
        // Given
        Product product = new Product();
        int negativeQuantity = -5;

        // When
        product.setQuantity(negativeQuantity);

        // Then
        assertThat(product.getQuantity()).isEqualTo(negativeQuantity);
    }

    @Test
    @DisplayName("Should handle null category")
    void properties_shouldHandleNullCategory_whenNullCategorySet() {
        // Given
        Product product = new Product();

        // When
        product.setCategory(null);

        // Then
        assertThat(product.getCategory()).isNull();
    }

    @Test
    @DisplayName("Should maintain relationship with category")
    void relationship_shouldMaintainCategoryRelationship_whenCategorySet() {
        // Given
        Product product = new Product();
        Category category = new Category("Electronics");
        category.setId(1L);

        // When
        product.setCategory(category);

        // Then
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getCategory().getId()).isEqualTo(1L);
        assertThat(product.getCategory().getName()).isEqualTo("Electronics");
    }

    @Test
    @DisplayName("Should demonstrate equals and hashCode functionality")
    void equalsAndHashCode_shouldWork_whenComparingProducts() {
        // Given
        Category category = new Category("Electronics");
        Product product1 = new Product(1L, "Laptop", 10, category);
        Product product2 = new Product(1L, "Laptop", 10, category);
        Product product3 = new Product(2L, "Phone", 5, category);

        // Then
        assertThat(product1).isEqualTo(product2);
        assertThat(product1).isNotEqualTo(product3);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @Test
    @DisplayName("Should demonstrate toString functionality")
    void toString_shouldContainAllFields_whenCalled() {
        // Given
        Category category = new Category("Electronics");
        Product product = new Product(1L, "Laptop", 10, category);

        // When
        String result = product.toString();

        // Then
        assertThat(result).contains("id=1");
        assertThat(result).contains("name=Laptop");
        assertThat(result).contains("quantity=10");
        assertThat(result).contains("category=");
    }
}