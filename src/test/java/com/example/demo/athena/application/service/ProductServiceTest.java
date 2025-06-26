package com.example.demo.athena.application.service;

import com.example.demo.athena.domain.model.Category;
import com.example.demo.athena.domain.model.Product;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductService.ProductPort productPort;

    @Mock
    private CategoryJpaRepository categoryJpaRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productPort, categoryJpaRepository);
    }

    @Test
    @DisplayName("Should create product successfully when valid category exists")
    void createProduct_shouldReturnProduct_whenValidCategoryExists() {
        // Given
        Long categoryId = 1L;
        Category category = new Category("Electronics");
        category.setId(categoryId);
        
        Product inputProduct = new Product();
        inputProduct.setName("Laptop");
        inputProduct.setQuantity(10);
        inputProduct.setCategory(category);
        
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Laptop");
        expectedProduct.setQuantity(10);
        expectedProduct.setCategory(category);
        
        when(categoryJpaRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productPort.save(any(Product.class))).thenReturn(expectedProduct);

        // When
        Product result = productService.createProduct(inputProduct);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getQuantity()).isEqualTo(10);
        assertThat(result.getCategory()).isEqualTo(category);
        
        verify(categoryJpaRepository, times(1)).findById(categoryId);
        verify(productPort, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when category does not exist")
    void createProduct_shouldThrowException_whenCategoryDoesNotExist() {
        // Given
        Long categoryId = 999L;
        Category category = new Category("NonExistent");
        category.setId(categoryId);
        
        Product inputProduct = new Product();
        inputProduct.setName("Laptop");
        inputProduct.setQuantity(10);
        inputProduct.setCategory(category);
        
        when(categoryJpaRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.createProduct(inputProduct))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Category not found");
        
        verify(categoryJpaRepository, times(1)).findById(categoryId);
        verify(productPort, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should return all products when getAllProducts is called")
    void getAllProducts_shouldReturnAllProducts_whenCalled() {
        // Given
        Category category = new Category("Electronics");
        List<Product> expectedProducts = Arrays.asList(
            createProduct(1L, "Laptop", 10, category),
            createProduct(2L, "Phone", 5, category)
        );
        
        when(productPort.findAll()).thenReturn(expectedProducts);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expectedProducts);
        
        verify(productPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return product when valid ID exists")
    void getProductById_shouldReturnProduct_whenValidIdExists() {
        // Given
        Long productId = 1L;
        Category category = new Category("Electronics");
        Product expectedProduct = createProduct(productId, "Laptop", 10, category);
        
        when(productPort.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // When
        Optional<Product> result = productService.getProductById(productId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedProduct);
        
        verify(productPort, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Should return empty Optional when product ID does not exist")
    void getProductById_shouldReturnEmpty_whenProductIdDoesNotExist() {
        // Given
        Long productId = 999L;
        
        when(productPort.findById(productId)).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productService.getProductById(productId);

        // Then
        assertThat(result).isEmpty();
        
        verify(productPort, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Should remove product when valid ID is provided")
    void removeProduct_shouldCallDeleteById_whenValidIdProvided() {
        // Given
        Long productId = 1L;
        
        doNothing().when(productPort).deleteById(productId);

        // When
        productService.removeProduct(productId);

        // Then
        verify(productPort, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Should handle product with zero quantity")
    void createProduct_shouldHandleZeroQuantity_whenZeroQuantityProvided() {
        // Given
        Long categoryId = 1L;
        Category category = new Category("Electronics");
        category.setId(categoryId);
        
        Product inputProduct = new Product();
        inputProduct.setName("Out of Stock Item");
        inputProduct.setQuantity(0);
        inputProduct.setCategory(category);
        
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Out of Stock Item");
        expectedProduct.setQuantity(0);
        expectedProduct.setCategory(category);
        
        when(categoryJpaRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productPort.save(any(Product.class))).thenReturn(expectedProduct);

        // When
        Product result = productService.createProduct(inputProduct);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(0);
        
        verify(categoryJpaRepository, times(1)).findById(categoryId);
        verify(productPort, times(1)).save(any(Product.class));
    }

    private Product createProduct(Long id, String name, int quantity, Category category) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setQuantity(quantity);
        product.setCategory(category);
        return product;
    }
}