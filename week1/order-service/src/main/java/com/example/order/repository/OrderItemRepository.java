package com.example.order.repository;

import com.example.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Find order items by order ID
    List<OrderItem> findByOrderId(Long orderId);
    
    // Find order items by product ID
    List<OrderItem> findByProductId(Long productId);
    
    // Find order items by order ID and product ID
    List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
} 