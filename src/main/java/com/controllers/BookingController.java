package com.controllers;

import com.dtos.booking.BookingMakeDTO;
import com.models.Booking;
import com.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return this.bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable UUID id) {
        return this.bookingService.getBooking(id);
    }

    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingMakeDTO bookingMakeDto) {
        return this.bookingService.createBooking(bookingMakeDto);
    }

    @PostMapping("/cancel/{id}")
    public Booking cancelBooking(@PathVariable UUID id) {
        return this.bookingService.cancelBooking(id);
    }
}
