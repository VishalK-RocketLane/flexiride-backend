package com.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.models.Customer;
import com.services.CustomerService;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{email}")
    public Customer getCustomerFromEmail(@PathVariable String email) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(email.toLowerCase())
        || SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals("admin@gmail.com")) {
            Customer customer = customerService.getCustomerByEmail(email);
            customer.setHashedPassword(null);
            return customer;
        }
        else{
            throw new AccessDeniedException("You are not allowed to access this resource");
        }
    }
}
