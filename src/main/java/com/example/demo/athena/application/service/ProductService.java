package com.example.demo.athena.application.service;
import com.example.demo.athena.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }


    public Product createProduct(Product product) {
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
