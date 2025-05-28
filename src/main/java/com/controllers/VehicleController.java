package com.controllers;

import com.dtos.vehicle.VehicleFilterDTO;
import com.dtos.vehicle.VehicleUpdateDTO;
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

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable UUID id){
        return vehicleService.getVehicle(id);
    }

    @PostMapping("/filter")
    public List<Vehicle> filterVehicles(@RequestBody VehicleFilterDTO vehicleFilterDto){
        return vehicleService.getFilteredVehicles(vehicleFilterDto);
    }

    @PostMapping("/create")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.createVehicle(vehicle);
    }

    @PostMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable UUID id, @RequestBody VehicleUpdateDTO vehicleUpdateDto){
        return vehicleService.updateVehicle(id, vehicleUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable UUID id){
        vehicleService.deleteVehicle(id);
    }
}
