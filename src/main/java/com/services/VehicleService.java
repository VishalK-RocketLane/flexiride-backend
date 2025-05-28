package com.services;

import com.models.Vehicle;
import com.repos.VehicleRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicle(UUID id) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        return vehicle.orElse(null);
    }
}
