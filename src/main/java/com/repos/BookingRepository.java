package com.repos;

import com.models.Booking;
import com.models.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<List<Booking>> findByVehicleId(UUID vehicleId);
}
