package com.example.demo.athena.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private String category;

    public Product() {}

    public Product(String name, Long id, int quantity, String category) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.category = category;
    }
}