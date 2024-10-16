package com.docsehr.flowerhub.repository;

import com.docsehr.flowerhub.model.mongo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAllByUserId(Long userId);
}