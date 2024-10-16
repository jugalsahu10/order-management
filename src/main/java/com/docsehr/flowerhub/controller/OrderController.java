package com.docsehr.flowerhub.controller;

import com.docsehr.flowerhub.model.dto.ProductRequest;
import com.docsehr.flowerhub.model.mongo.OldProduct;
import com.docsehr.flowerhub.model.mongo.Order;
import com.docsehr.flowerhub.service.OldProductService;
import com.docsehr.flowerhub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(
            @RequestParam("userId") Long userId,
            @RequestBody List<ProductRequest> productRequests) {
        Order order = orderService.placeAnOrder(userId, productRequests);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}

