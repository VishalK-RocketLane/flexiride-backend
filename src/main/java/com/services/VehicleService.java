package com.services;

import com.models.Vehicle;
import com.repos.VehicleRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> GetAllVehicles(){
        return vehicleRepository.findAll();
    }
}
