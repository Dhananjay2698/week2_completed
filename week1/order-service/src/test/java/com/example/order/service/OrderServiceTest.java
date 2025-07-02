package com.example.order.service;

import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetAllOrders() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setStatus("PENDING");

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setStatus("PENDING");

        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        var result = orderService.getOrderById(1L);

        assertEquals("PENDING", result.get().getStatus());
    }
} 