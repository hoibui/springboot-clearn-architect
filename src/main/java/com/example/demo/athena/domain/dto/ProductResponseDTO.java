package com.example.demo.athena.domain.dto;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private int quantity;
    private String categoryName;

    public ProductResponseDTO(Long id, String name, int quantity, String categoryName) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.categoryName = categoryName;
    }

    // getters and setters
}
