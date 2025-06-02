package com.controllers;

import com.dtos.vehicle.VehicleFilterDTO;
import com.dtos.vehicle.VehicleUpdateDTO;
import com.models.Vehicle;
import com.services.VehicleService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private boolean checkIfAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals("admin@gmail.com");
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
        if(checkIfAdmin()){
            return vehicleService.createVehicle(vehicle);
        }
        throw new AccessDeniedException("You are not allowed to create a vehicle");
    }

    @PostMapping("/edit/{id}")
    public Vehicle updateVehicle(@PathVariable UUID id, @RequestBody VehicleUpdateDTO vehicleUpdateDto){
        if(checkIfAdmin()){
            return vehicleService.updateVehicle(id, vehicleUpdateDto);
        }
        throw new AccessDeniedException("You are not allowed to edit this vehicle");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVehicle(@PathVariable UUID id){
        if(checkIfAdmin()){
            vehicleService.deleteVehicle(id);
        }
        throw new AccessDeniedException("You are not allowed to delete this vehicle");
    }
}
