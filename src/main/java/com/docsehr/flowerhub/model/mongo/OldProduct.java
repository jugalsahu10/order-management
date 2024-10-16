package com.docsehr.flowerhub.model.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "products")
@Data
public class OldProduct {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    // Getters and Setters
}

