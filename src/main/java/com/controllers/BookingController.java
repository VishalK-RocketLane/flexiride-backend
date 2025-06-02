package com.controllers;

import com.dtos.booking.BookingMakeDTO;
import com.models.Booking;
import com.models.Customer;
import com.services.BookingService;
import com.services.CustomerService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;

    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return this.bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable UUID id) {
        return this.bookingService.getBooking(id);
    }

    @GetMapping("/email/{email}")
    public List<Booking> getBookingsByEmail(@PathVariable String email) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(email.toLowerCase())) {
            return this.bookingService.getBookingsByEmail(email);
        }
        throw new AccessDeniedException("You are not allowed to access this resource");
    }

    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingMakeDTO bookingMakeDto) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(bookingMakeDto.getEmail().toLowerCase())) {
            return this.bookingService.createBooking(bookingMakeDto);
        }
        throw new AccessDeniedException("You are not allowed to make this booking");
    }

    @PostMapping("/cancel/{id}")
    public Booking cancelBooking(@PathVariable UUID id) {
        Booking booking = this.bookingService.getBooking(id);
        if(booking == null) return null;

        Customer customer = this.customerService.getCustomerById(booking.getCustomerId());
        if(customer == null) return null;

        if(SecurityContextHolder.getContext().getAuthentication().getName().toLowerCase().equals(customer.getEmail().toLowerCase())) {
            return this.bookingService.cancelBooking(id);
        }
        else {
            throw new AccessDeniedException("You are not allowed to cancel this booking");
        }
    }
}
