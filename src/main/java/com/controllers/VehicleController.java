package com.controllers;

import com.dtos.VehicleUpdateDTO;
import com.models.Vehicle;
import com.services.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable UUID id){
        return vehicleService.getVehicle(id);
    }

    @PostMapping("/create")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.createVehicle(vehicle);
    }

    @PostMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable UUID id, @RequestBody VehicleUpdateDTO vehicleUpdateDTO){
        return vehicleService.updateVehicle(id, vehicleUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable UUID id){
        vehicleService.deleteVehicle(id);
    }
}
