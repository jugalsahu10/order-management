package com.docsehr.flowerhub.model.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "job")
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;  // Guest User ID
    private String description; // Reference to MongoDB product
    private Long userId;
}

