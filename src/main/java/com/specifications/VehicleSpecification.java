package com.specifications;

import com.models.Booking;
import com.models.Vehicle;
import com.services.BookingService;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class VehicleSpecification {
    public static Specification<Vehicle> hasTypes(List<String> types) {
        return (root, query, cb) -> {
            if(types == null || types.isEmpty()) {
                return null;
            }
            return root.get("type").in(types);
        };
    }

    public static Specification<Vehicle> hasBrands(List<String> brands) {
        return (root, query, cb) -> {
            if(brands == null || brands.isEmpty()) {
                return null;
            }
            return root.get("brand").in(brands);
        };
    }

    public static Specification<Vehicle> hasModels(List<String> models) {
        return (root, query, cb) -> {
            if(models == null || models.isEmpty()) {
                return null;
            }
            return root.get("model").in(models);
        };
    }

    public static Specification<Vehicle> priceInRange(Float startPrice, Float endPrice) {
        return (root, query, cb) -> {
            if(startPrice != null && endPrice != null) {
                return cb.between(root.get("pricePerDay"), startPrice, endPrice);
            }
            else if(startPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("pricePerDay"), startPrice);
            }
            else if(endPrice != null) {
                return cb.lessThanOrEqualTo(root.get("pricePerDay"), endPrice);
            }
            else {
                return null;
            }
        };
    }

    public static Specification<Vehicle> dateInRange(String startDateString, String endDateString) {
        LocalDate startDate = startDateString != null ? LocalDate.parse(startDateString) : null;
        LocalDate endDate = endDateString != null ? LocalDate.parse(endDateString) : null;
    
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return null;
            }
    
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Booking> bookingRoot = subquery.from(Booking.class);
    
            subquery.select(bookingRoot.get("vehicleId"))
                .where(
                    cb.and(
                        cb.equal(bookingRoot.get("vehicleId"), root.get("id")),
                        cb.notEqual(bookingRoot.get("status"), "CANCELLED"), 
                        cb.lessThanOrEqualTo(bookingRoot.get("startDate"), endDate),
                        cb.greaterThanOrEqualTo(bookingRoot.get("endDate"), startDate)
                    )
                );
    
            return cb.not(cb.exists(subquery));
        };
    }
      
}
