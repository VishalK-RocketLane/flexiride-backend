package com.schedulers;

import com.models.Booking;
import com.repos.BookingRepository;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingStatusScheduler {

    private final BookingRepository bookingRepository;

    public BookingStatusScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Runs daily at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledUpdate() {
        updateBookingStatuses();
    }

    // Runs once when the application starts
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        updateBookingStatuses();
    }

    // Shared logic
    private void updateBookingStatuses() {
        LocalDate today = LocalDate.now();
        List<Booking> bookings = bookingRepository.findAll();

        for (Booking booking : bookings) {
            if (booking.getEndDate().isBefore(today) && !booking.getStatus().equals("CANCELLED")) {
                booking.setStatus("COMPLETED");
            }
        }

        bookingRepository.saveAll(bookings);
    }
}
