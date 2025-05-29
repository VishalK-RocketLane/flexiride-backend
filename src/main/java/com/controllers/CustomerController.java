package com.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.models.Customer;
import com.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{email}")
    public Customer getCustomerFromEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        customer.setHashedPassword(null);
        return customer;
    }
}
