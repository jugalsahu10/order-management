package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.dto.ProductRequest;
import com.docsehr.flowerhub.model.mongo.OldProduct;
import com.docsehr.flowerhub.model.mongo.Order;
import com.docsehr.flowerhub.model.mysql.Product;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional // roll back if an error occurs
    public Order placeAnOrder(Long userId, List<ProductRequest> productRequests) {
        User user = userService.getUser(userId); // verify user exists
        List<Long> productIds = productRequests.stream().map(ProductRequest::getId).collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(productIds);
        // validate product ids
        if(productIds.size() != products.size()) {
            throw new IllegalArgumentException("Invalid product IDs");
        }

        Map<Long, List<Product>> productsById = products.stream().collect(Collectors.groupingBy(Product::getId));

        // validate product quantity
        productRequests.forEach(productRequest -> {
            Product product = productsById.get(productRequest.getId()).get(0);
            if (productRequest.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Insufficient product quantity");
            }
        });

        // get total
        List<Long> itemTotalList = productRequests.stream().map(productRequest -> {
            Product product = productsById.get(productRequest.getId()).get(0);
            return productRequest.getQuantity() * product.getPrice();
        }).collect(Collectors.toList());

        AtomicReference<Long> total = new AtomicReference<>((long) 0);
        itemTotalList.forEach( itemTotal -> total.updateAndGet(v -> v + itemTotal));


        // update product quantity
        productRequests.forEach(productRequest -> {
            Product product = productsById.get(productRequest.getId()).get(0);
            product.setQuantity(product.getQuantity() - productRequest.getQuantity());
            productService.updateProduct(product);
        });

        // finally place an order
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

