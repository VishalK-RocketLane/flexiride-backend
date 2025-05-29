package com.dtos.customer;

import lombok.Data;

@Data
public class CustomerRegisterDTO {
    private String email;
    private String password;
    private String name;
}
