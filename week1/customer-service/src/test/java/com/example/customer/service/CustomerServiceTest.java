package com.example.customer.service;

import com.example.customer.entity.Customer;
import com.example.customer.repository.CustomerRepository;
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
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(customer));

        var result = customerService.getCustomerById(1L);

        assertEquals("John Doe", result.get().getName());
    }
} 