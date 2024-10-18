package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.dto.ProductDTO;
import com.docsehr.flowerhub.model.mongo.Order;
import com.docsehr.flowerhub.model.mysql.Product;
import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired private UserService userService;
    @Autowired private ProductService productService;
    @Autowired private OrderRepository orderRepository;

    @Transactional
    // roll back if an error occurs
    public Order placeOrder(Long userId, List<ProductDTO> productDTOS) {
        userService.validateUser(userId);

        List<Long> productIds = productDTOS.stream().map(ProductDTO::getProductId).collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(productIds);
        // validate product ids
        if (productIds.size() != products.size()) {
            throw new IllegalArgumentException("Invalid product IDs");
        }
        Map<Long, List<Product>> productsById = products.stream().collect(Collectors.groupingBy(Product::getId));
        validateProductQuantity(productDTOS, productsById);
        Long orderTotal = getOrderTotal(productDTOS, productsById);
        updateProductQuantity(productDTOS, productsById);

        Order order = getOrder(userId, productDTOS, orderTotal);
        return orderRepository.save(order);
    }

    private void updateProductQuantity(List<ProductDTO> productDTOS, Map<Long, List<Product>> productsById) {
        productDTOS.forEach(productDTO -> {
            Product product = productsById.get(productDTO.getProductId()).get(0);
            product.setQuantity(product.getQuantity() - productDTO.getQuantity());
            productService.updateProduct(product);
        });
    }

    private static Order getOrder(Long userId, List<ProductDTO> productDTOS, Long total) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProducts(productDTOS);
        order.setTotal(total);
        return order;
    }

    private static Long getOrderTotal(List<ProductDTO> productDTOS, Map<Long, List<Product>> productsById) {
        return productDTOS.stream().map(productDTO -> {
            Product product = productsById.get(productDTO.getProductId()).get(0);
            return productDTO.getQuantity() * product.getPrice();
        }).reduce(0L, Long::sum);
    }

    private static void validateProductQuantity(List<ProductDTO> productDTOS, Map<Long, List<Product>> productsById) {
        for (ProductDTO productDTO : productDTOS) {
            Product product = productsById.get(productDTO.getProductId()).get(0);
            if (productDTO.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Insufficient product quantity for product ID: " + productDTO.getProductId());
            }
        }
    }

    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found for ID: " + orderId));
    }
}

