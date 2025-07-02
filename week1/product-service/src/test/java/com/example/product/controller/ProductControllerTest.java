package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.example.product.config.JwtAuthenticationFilter;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));

        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productService.getProductById(1L)).thenReturn(java.util.Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class NoSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
            return http.build();
        }
    }
} 