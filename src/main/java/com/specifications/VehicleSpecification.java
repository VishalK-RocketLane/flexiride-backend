package com.specifications;

import com.models.Booking;
import com.models.Vehicle;
import com.services.BookingService;

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
    
            // Subquery to find vehicles with conflicting bookings
            var subquery = query.subquery(UUID.class);
            var bookingRoot = subquery.from(Booking.class);
    
            subquery.select(bookingRoot.get("vehicle").get("id"))
                    .where(
                        cb.and(
                            cb.equal(bookingRoot.get("vehicle").get("id"), root.get("id")),
                            cb.lessThanOrEqualTo(bookingRoot.get("startDate"), endDate),
                            cb.greaterThanOrEqualTo(bookingRoot.get("endDate"), startDate)
                        )
                    );
    
            // Exclude vehicles with overlapping bookings
            return cb.not(cb.exists(subquery));
        };
    }    
}
