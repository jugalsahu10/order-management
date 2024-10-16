package com.docsehr.flowerhub.model.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  // Guest User ID
    private String email; // Reference to MongoDB product
    private String address;
    // Getters and Setters
}

