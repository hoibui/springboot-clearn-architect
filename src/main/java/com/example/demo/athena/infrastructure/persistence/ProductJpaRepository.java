package com.example.demo.athena.infrastructure.persistence;

import com.example.demo.athena.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
