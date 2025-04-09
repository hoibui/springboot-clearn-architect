package com.example.demo.athena.application.service;
import com.example.demo.athena.domain.model.Category;
import com.example.demo.athena.domain.model.Product;
import com.example.demo.athena.infrastructure.persistence.CategoryJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductPort productPort;
    private final CategoryJpaRepository categoryJpaRepository;

    public ProductService(ProductPort productPort, CategoryJpaRepository categoryJpaRepository) {
        this.productPort = productPort;
        this.categoryJpaRepository = categoryJpaRepository;
    }


    public Product createProduct(Product product) {
        Category category = categoryJpaRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        return productPort.save(product);
    }

    public List<Product> getAllProducts() {
        return productPort.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productPort.findById(id);
    }

    public void removeProduct(Long id) {
        productPort.deleteById(id);
    }


    public interface ProductPort {
        Product save(Product product);
        List<Product> findAll();
        Optional<Product> findById(Long id);
        void deleteById(Long id);
    }
}
