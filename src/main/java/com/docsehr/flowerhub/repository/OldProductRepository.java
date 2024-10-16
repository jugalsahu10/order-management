package com.docsehr.flowerhub.repository;

import com.docsehr.flowerhub.model.mongo.OldProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OldProductRepository extends MongoRepository<OldProduct, String> {
}