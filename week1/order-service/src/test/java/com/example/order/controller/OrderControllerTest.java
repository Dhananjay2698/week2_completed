package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import com.example.order.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void testGetAllOrders() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setStatus("PENDING");

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setStatus("PENDING");

        when(orderService.getOrderById(1L)).thenReturn(java.util.Optional.of(order));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
    }
} 