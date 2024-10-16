package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.mongo.OldProduct;
import com.docsehr.flowerhub.repository.OldProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OldProductService {
    @Autowired
    private OldProductRepository oldProductRepository;
    public List<OldProduct> getAllProducts() {
        return oldProductRepository.findAll();
    }

    public OldProduct saveProduct(OldProduct oldProduct) {
        return oldProductRepository.save(oldProduct);
    }
}

