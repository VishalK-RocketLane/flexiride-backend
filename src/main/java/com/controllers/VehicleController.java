package com.controllers;

import com.models.Vehicle;
import com.services.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    public List<Vehicle> GetAllVehicles() {
        return vehicleService.GetAllVehicles();
    }
}
