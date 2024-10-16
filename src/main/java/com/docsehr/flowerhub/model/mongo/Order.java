package com.docsehr.flowerhub.model.mongo;

import com.docsehr.flowerhub.model.mysql.Product;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
@Data
public class Order {
    @Id
    private String id;
    private Long userId;
    private List<Product> products = new ArrayList<>();
    private Long total; // in cents
    // Getters and Setters
}

