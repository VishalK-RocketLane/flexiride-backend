package com.services;


import com.models.Booking;
import com.repos.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return this.bookingRepository.findAll();
    }

    public Booking getBooking(UUID id) {
        Optional<Booking> booking = this.bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return this.bookingRepository.save(booking);
    }
}
