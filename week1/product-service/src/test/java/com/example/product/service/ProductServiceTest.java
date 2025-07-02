package com.example.product.service;

import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        var result = productService.getProductById(1L);

        assertEquals("Laptop", result.get().getName());
    }
} 