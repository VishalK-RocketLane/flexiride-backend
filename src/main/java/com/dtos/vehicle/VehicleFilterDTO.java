package com.dtos.vehicle;

import java.util.List;

import lombok.Data;

@Data
public class VehicleFilterDTO {
    private List<String> brands;
    private List<String> models;
    private List<String> types;
    private Float startPrice;
    private Float endPrice;
}
