package com.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtos.auth.AuthRequestDTO;
import com.dtos.customer.CustomerDTO;
import com.dtos.customer.CustomerRegisterDTO;
import com.models.Customer;
import com.services.CustomerService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomerService customerService;
    
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

   @PostMapping("/login")
   public CustomerDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
       return customerService.authenticate(authRequestDTO.getEmail(), authRequestDTO.getPassword());
   }

    @PostMapping("/admin/login")
    public CustomerDTO adminLogin(@RequestBody AuthRequestDTO authRequestDTO) {
        if(authRequestDTO.getEmail().equals("admin") && authRequestDTO.getPassword().equals("admin")){
            return new CustomerDTO(authRequestDTO.getEmail(), "admin"); // TODO: change this to admin dto later, for now just return a dummy dto for admin men
        }
        return null;
    }
    
    @PostMapping("/register")
    public Customer register(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        return customerService.registerCustomer(customerRegisterDTO);
    }
}
