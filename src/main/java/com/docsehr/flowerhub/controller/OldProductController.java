package com.docsehr.flowerhub.controller;

import com.docsehr.flowerhub.model.mongo.OldProduct;
import com.docsehr.flowerhub.service.OldProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class OldProductController {
    @Autowired
    private OldProductService oldProductService;
    @GetMapping
    public List<OldProduct> getAllProducts() {
        return oldProductService.getAllProducts();
    }

    @PostMapping
    public OldProduct addProduct(@RequestBody OldProduct oldProduct) {
        return oldProductService.saveProduct(oldProduct);
    }
}

