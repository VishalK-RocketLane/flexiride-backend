package com.specifications;

import com.models.Vehicle;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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
}
