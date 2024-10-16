package com.docsehr.flowerhub.repository;

import com.docsehr.flowerhub.model.mysql.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
