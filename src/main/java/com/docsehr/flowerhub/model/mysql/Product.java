package com.docsehr.flowerhub.model.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  // Guest User ID
    private Long price; // in cents
    private Integer quantity;
    // Getters and Setters
}

