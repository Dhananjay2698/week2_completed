package com.example.customer.service;

import com.example.customer.entity.Customer;
import com.example.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
class CustomerServiceAspect {
    Logger logger = LoggerFactory.getLogger(CustomerServiceAspect.class);
    @Pointcut("execution(* com.example.customer.service.CustomerService.update*(..))")
    void updateCustomer() {}
    @Before("updateCustomer()")
    void logUpdate(JoinPoint joinPoint) {
        logger.info("Customer update called: " + joinPoint.getSignature());
    }
    @Before("execution(* com.example.customer.service.CustomerService.*(..))")
    void logAll(JoinPoint joinPoint) {
        logger.info("CustomerService method: " + joinPoint.getSignature());
    }
}

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Save or update customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update customer details
    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            return customerRepository.save(customer);
        });
    }

    // Delete customer
    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 