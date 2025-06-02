package com.services;

import com.dtos.customer.CustomerDTO;
import com.dtos.customer.CustomerRegisterDTO;
import com.models.Customer;
import com.repos.CustomerRepository;
import com.utils.PasswordUtil;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(CustomerRegisterDTO customerRegisterDto) {
        // Check if email already exists
        if (customerRepository.findByEmail(customerRegisterDto.getEmail()).isPresent()) {
            return null;
        }

        Customer customer = new Customer();
        customer.setEmail(customerRegisterDto.getEmail());
        customer.setName(customerRegisterDto.getName());
        customer.setHashedPassword(PasswordUtil.hashPassword(customerRegisterDto.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerDTO authenticate(String email, String password) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if (customer != null && PasswordUtil.checkPassword(password, customer.getHashedPassword())) {
            return new CustomerDTO(customer.getEmail(), customer.getName(), "CUSTOMER");
        }
        return null;
    }

    @Override
    public Customer loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        return customer; // Customer must implement UserDetails
    }
}
