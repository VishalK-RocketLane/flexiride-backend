package com.services;

import com.dtos.vehicle.VehicleFilterDTO;
import com.dtos.vehicle.VehicleUpdateDTO;
import com.models.Vehicle;
import com.repos.VehicleRepository;
import com.specifications.VehicleSpecification;

import org.springframework.data.jpa.domain.Specification;
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

    public List<Vehicle> getFilteredVehicles(VehicleFilterDTO vehicleFilterDto) {
        Specification<Vehicle> spec = Specification.where(VehicleSpecification.hasTypes(vehicleFilterDto.getTypes()))
            .and(VehicleSpecification.hasBrands(vehicleFilterDto.getBrands()))
            .and(VehicleSpecification.hasModels(vehicleFilterDto.getModels()))
            .and(VehicleSpecification.priceInRange(vehicleFilterDto.getStartPrice(), vehicleFilterDto.getEndPrice()))
            .and(VehicleSpecification.dateInRange(vehicleFilterDto.getStartDate(), vehicleFilterDto.getEndDate()));
        return this.vehicleRepository.findAll(spec);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return this.vehicleRepository.save(vehicle);
    }

    public Vehicle deleteVehicle(UUID id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElse(null);
        if(vehicle != null) {
            this.vehicleRepository.deleteById(id);
        }
        return vehicle;
    }

    public Vehicle updateVehicle(UUID id, VehicleUpdateDTO vehicleUpdateDto) {
        Optional<Vehicle> record = this.vehicleRepository.findById(id);
        if(record.isEmpty()) {
            return null;
        }
        
        Vehicle vehicle = record.get();
        if(vehicleUpdateDto.getBrand() != null) vehicle.setBrand(vehicleUpdateDto.getBrand());
        if(vehicleUpdateDto.getModel()!= null) vehicle.setModel(vehicleUpdateDto.getModel());
        if(vehicleUpdateDto.getType()!= null) vehicle.setType(vehicleUpdateDto.getType());
        if(vehicleUpdateDto.getPricePerDay()!= null) vehicle.setPricePerDay(vehicleUpdateDto.getPricePerDay());
        if(vehicleUpdateDto.getImageUrl()!= null) vehicle.setImageUrl(vehicleUpdateDto.getImageUrl());

        return this.vehicleRepository.save(vehicle);
    }
}
