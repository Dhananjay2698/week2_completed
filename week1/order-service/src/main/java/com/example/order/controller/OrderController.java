package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // ========== ORDER ENDPOINTS ==========
    
    // Get all orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // Get order by ID
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new order
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    // Update order
    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order updatedOrder = orderService.updateOrder(id, orderDetails);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete order
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // ========== ORDER ITEM ENDPOINTS ==========
    
    // Get all order items
    @GetMapping("/orders-items")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }
    
    // Get order items by order ID
    @GetMapping("/orders-items/orders/{id}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable("id") Long id) {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(id);
        return ResponseEntity.ok(orderItems);
    }
    
    // Create order item
    @PostMapping("/orders-items")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem createdOrderItem = orderService.createOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }
    
    // Update order item
    @PutMapping("/orders-items/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItemDetails) {
        OrderItem updatedOrderItem = orderService.updateOrderItem(id, orderItemDetails);
        if (updatedOrderItem != null) {
            return ResponseEntity.ok(updatedOrderItem);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete order item
    @DeleteMapping("/orders-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrderItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    
    // Get sum of total amount for a given order
    @GetMapping("/orders/{id}/total")
    public ResponseEntity<BigDecimal> getOrderTotal(@PathVariable Long id) {
        BigDecimal total = orderService.calculateOrderTotal(id);
        if (total != null) {
            return ResponseEntity.ok(total);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Get total orders placed by a customer
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
    
    // Count orders by customer
    @GetMapping("/customers/{customerId}/orders/count")
    public ResponseEntity<Long> getOrderCountByCustomer(@PathVariable Long customerId) {
        long count = orderService.getOrderCountByCustomer(customerId);
        return ResponseEntity.ok(count);
    }
    
    // Get orders by status
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    // Count distinct orders in which a given product appears
    @GetMapping("/products/{productId}/orders/count")
    public ResponseEntity<Long> getDistinctOrderCountByProduct(@PathVariable Long productId) {
        long count = orderService.getDistinctOrderCountByProduct(productId);
        return ResponseEntity.ok(count);
    }
    
    // Delete product association from given order
    @DeleteMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<Void> deleteProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        boolean deleted = orderService.deleteProductFromOrder(orderId, productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Count total purchases made by customer till date or for a given date range
    @GetMapping("/customers/{customerId}/purchases/count")
    public ResponseEntity<Long> getCustomerPurchaseCount(
            @PathVariable Long customerId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        long count = orderService.getCustomerPurchaseCount(customerId, startDate, endDate);
        return ResponseEntity.ok(count);
    }
} 