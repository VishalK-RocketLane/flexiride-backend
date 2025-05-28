package com.services;

import com.dtos.VehicleUpdateDTO;
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

    public Vehicle createVehicle(Vehicle vehicle) {
        return this.vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(UUID id) {
        this.vehicleRepository.deleteById(id);
    }

    public Vehicle updateVehicle(UUID id, VehicleUpdateDTO vehicleUpdateDTO) {
        Optional<Vehicle> record = this.vehicleRepository.findById(id);
        if(record.isEmpty()) {
            return null;
        }
        
        Vehicle vehicle = record.get();
        if(vehicleUpdateDTO.getBrand() != null) vehicle.setBrand(vehicleUpdateDTO.getBrand());
        if(vehicleUpdateDTO.getModel()!= null) vehicle.setModel(vehicleUpdateDTO.getModel());
        if(vehicleUpdateDTO.getType()!= null) vehicle.setType(vehicleUpdateDTO.getType());
        if(vehicleUpdateDTO.getPricePerDay()!= null) vehicle.setPricePerDay(vehicleUpdateDTO.getPricePerDay());
        if(vehicleUpdateDTO.getAdvance()!= null) vehicle.setAdvance(vehicleUpdateDTO.getAdvance());

        return this.vehicleRepository.save(vehicle);
    }
}
