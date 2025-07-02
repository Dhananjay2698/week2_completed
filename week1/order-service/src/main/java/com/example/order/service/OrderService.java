package com.example.order.service;

import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
class OrderServiceAspect {
    Logger logger = LoggerFactory.getLogger(OrderServiceAspect.class);
    @Pointcut("execution(* com.example.order.service.OrderService.update*(..))")
    void updateOrder() {}
    @Before("updateOrder()")
    void logUpdate(JoinPoint joinPoint) {
        logger.info("Order update called: " + joinPoint.getSignature());
    }
    @Before("execution(* com.example.order.service.OrderService.*(..))")
    void logAll(JoinPoint joinPoint) {
        logger.info("OrderService method: " + joinPoint.getSignature());
    }
}

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    // Create new order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    
    // Update order
    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setCustomerId(orderDetails.getCustomerId());
            order.setStatus(orderDetails.getStatus());
            order.setOrderedDate(orderDetails.getOrderedDate());
            order.setUpdatedDate(orderDetails.getUpdatedDate());
            return orderRepository.save(order);
        }
        return null;
    }
    
    // Delete order
    public boolean deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get orders by customer ID
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    // Get all order items
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
    
    // Get order items by order ID
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
    
    // Create order item
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    
    // Update order item
    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
        if (optionalOrderItem.isPresent()) {
            OrderItem orderItem = optionalOrderItem.get();
            orderItem.setOrderId(orderItemDetails.getOrderId());
            orderItem.setProductId(orderItemDetails.getProductId());
            orderItem.setQuantity(orderItemDetails.getQuantity());
            return orderItemRepository.save(orderItem);
        }
        return null;
    }
    
    // Delete order item
    public boolean deleteOrderItem(Long id) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
        if (optionalOrderItem.isPresent()) {
            orderItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Calculate total amount for a given order
    public BigDecimal calculateOrderTotal(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) {
            return null;
        }
        
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            total = total.add(BigDecimal.valueOf(item.getQuantity()));
        }
        return total;
    }

    // Get order count by customer
    public long getOrderCountByCustomer(Long customerId) {
        return orderRepository.countByCustomerId(customerId);
    }

    // Count distinct orders in which a given product appears
    public long getDistinctOrderCountByProduct(Long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
        return orderItems.stream()
                .map(OrderItem::getOrderId)
                .distinct()
                .count();
    }

    // Delete product association from given order
    public boolean deleteProductFromOrder(Long orderId, Long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderIdAndProductId(orderId, productId);
        if (!orderItems.isEmpty()) {
            orderItemRepository.deleteAll(orderItems);
            return true;
        }
        return false;
    }

    // Count total purchases made by customer till date or for a given date range
    public long getCustomerPurchaseCount(Long customerId, String startDate, String endDate) {
        if (startDate != null && endDate != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
                LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
                return orderRepository.countByCustomerIdAndOrderedDateBetween(customerId, startDateTime, endDateTime);
            } catch (Exception e) {
                // If date parsing fails, return total count
                return orderRepository.countByCustomerId(customerId);
            }
        } else {
            return orderRepository.countByCustomerId(customerId);
        }
    }
} 