package com.example.customer.controller;

import com.example.customer.entity.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void testGetAllCustomers() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerService.getCustomerById(1L)).thenReturn(java.util.Optional.of(customer));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk());
    }
} 