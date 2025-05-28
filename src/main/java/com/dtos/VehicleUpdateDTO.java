package com.dtos;

import lombok.Data;

@Data
public class VehicleUpdateDTO {
    private String brand;
    private String model;
    private String type;
    private Float pricePerDay;
    private Float advance;
}
