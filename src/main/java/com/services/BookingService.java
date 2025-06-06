package com.services;


import com.dtos.booking.BookingMakeDTO;
import com.models.Booking;
import com.models.Customer;
import com.models.Vehicle;
import com.repos.BookingRepository;
import com.repos.CustomerRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    public List<Booking> getAllBookings() {
        return this.bookingRepository.findAll();
    }

    public Booking getBooking(UUID id) {
        Optional<Booking> booking = this.bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public List<Booking> getBookingsByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if(customer == null) {
            return null;
        }
        return this.bookingRepository.findByCustomerId(customer.getId());
    }

    public Booking createBooking(BookingMakeDTO bookingMakeDto) {
        Booking booking = new Booking();
        Optional<Customer> customer = customerRepository.findByEmail(bookingMakeDto.getEmail());
        if(customer.isEmpty()) {
            return null;
        }
        booking.setCustomerId(customer.get().getId());
        booking.setVehicleId(bookingMakeDto.getVehicleId());
        booking.setStartDate(LocalDate.parse(bookingMakeDto.getStartDate()));
        booking.setEndDate(LocalDate.parse(bookingMakeDto.getEndDate()));
        booking.setStatus("ACTIVE");

        return this.bookingRepository.save(booking);
    }

    public Booking cancelBooking(UUID id) {
        Optional<Booking> booking = this.bookingRepository.findById(id);
        if(booking.isEmpty()) {
            return null;
        }
        booking.get().setStatus("CANCELLED");
        return this.bookingRepository.save(booking.get());
    }
}
