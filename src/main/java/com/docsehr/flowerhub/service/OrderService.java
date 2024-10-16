package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.dto.ProductRequest;
import com.docsehr.flowerhub.model.mongo.OldProduct;
import com.docsehr.flowerhub.model.mongo.Order;
import com.docsehr.flowerhub.model.mysql.Product;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;

    public Order placeAnOrder(Long userId, List<ProductRequest> productRequests) {
        User user = userService.getUser(userId);
        List<Long> productIds = productRequests.stream().map(ProductRequest::getId).collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(productIds);
        if(productIds.size() != products.size()) {
            throw new IllegalArgumentException("Invalid product IDs");
        }

        Map<Long, List<Product>> productsById = products.stream().collect(Collectors.groupingBy(Product::getId));

        productRequests.forEach(productRequest -> {
            Product product = productsById.get(productRequest.getId()).get(0);
            if (productRequest.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Insufficient product quantity");
            }
        });

        List<Long> itemTotalList = productRequests.stream().map(productRequest -> {
            Product product = productsById.get(productRequest.getId()).get(0);
            return productRequest.getQuantity() * product.getPrice();
        }).collect(Collectors.toList());

        AtomicReference<Long> total = new AtomicReference<>((long) 0);
        itemTotalList.forEach( itemTotal -> total.updateAndGet(v -> v + itemTotal));

        Order order = new Order();
        order.setUserId(userId);
        order.setProducts(products);
        order.setTotal(total.get());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).get();
    }
}

