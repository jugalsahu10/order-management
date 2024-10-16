package com.docsehr.flowerhub.model.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "favorites")
@Data
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;  // Guest User ID
    private String productId; // Reference to MongoDB product
    // Getters and Setters
}

