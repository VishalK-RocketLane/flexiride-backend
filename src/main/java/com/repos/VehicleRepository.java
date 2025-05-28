package com.repos;

import com.models.Vehicle;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> { }
