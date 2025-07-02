package com.example.order.repository;

import com.example.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find all orders
    List<Order> findAll();

    // Find order by ID
    Optional<Order> findById(Long id);

    // Find orders by customer ID
    List<Order> findByCustomerId(Long customerId);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Count orders by customer ID
    long countByCustomerId(Long customerId);

    // Count orders by customer ID and date range
    @Query("SELECT COUNT(o) FROM Order o WHERE o.customerId = :customerId AND o.orderedDate BETWEEN :startDate AND :endDate")
    long countByCustomerIdAndOrderedDateBetween(@Param("customerId") Long customerId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);

    // Delete order by ID
    void deleteById(Long id);
} 