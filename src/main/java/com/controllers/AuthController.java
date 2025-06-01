package com.controllers;

import com.dtos.auth.AuthRequestDTO;
import com.dtos.auth.AuthResponseDTO;
import com.dtos.customer.CustomerRegisterDTO;
import com.models.Customer;
import com.services.CustomerService;
import com.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static String ADMIN_EMAIL = "admin@gmail.com";
    private static String ADMIN_PASSWORD = "password";

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(CustomerService customerService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail().toLowerCase(),
                        authRequestDTO.getPassword()
                )
        );

        // Load user info and generate jwt
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        // Role based on email
        String role = userDetails.getUsername().equals("admin@gmail.com")? "ADMIN" : "CUSTOMER";

        // Return token + email + role + name
        return new AuthResponseDTO(token, userDetails.getUsername(), role, customerService.loadUserByUsername(authRequestDTO.getEmail()).getName());
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        try{
            Customer newCustomer = customerService.registerCustomer(customerRegisterDTO);
            UserDetails userDetails = customerService.loadUserByUsername(newCustomer.getEmail());
            String token = jwtService.generateToken(userDetails);
            return new AuthResponseDTO(token, newCustomer.getEmail(), "CUSTOMER", newCustomer.getName());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
