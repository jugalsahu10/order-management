package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.mysql.Product;
import com.docsehr.flowerhub.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public Product addProduct(Product product) {
    /*
        Optional<Product> dbProduct = productRepository.findById(product.getId());
        if (dbProduct.isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        */
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Optional<Product> dbProduct = productRepository.findById(product.getId());
        if (!dbProduct.isPresent()) {
            throw new RuntimeException("Product don't exist");
        }
        dbProduct.get().setName(product.getName());
        dbProduct.get().setPrice(product.getPrice());
        dbProduct.get().setQuantity(product.getQuantity());
        return productRepository.save(product);
    }
}

