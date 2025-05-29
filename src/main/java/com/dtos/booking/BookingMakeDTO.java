package com.dtos.booking;

import java.util.UUID;

import lombok.Data;

@Data
public class BookingMakeDTO {
    private String email;
    private UUID vehicleId;
    private String startDate;
    private String endDate;
}
