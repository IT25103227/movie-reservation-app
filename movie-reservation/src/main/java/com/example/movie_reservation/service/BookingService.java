package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final ParkingSlotRepository parkingRepo;

    public BookingService(BookingRepository bookingRepo, ParkingSlotRepository parkingRepo) {
        this.bookingRepo = bookingRepo;
        this.parkingRepo = parkingRepo;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    @Transactional
    public Booking processBooking(Booking booking) {
        if (booking.getStatus() == null) {
            booking.setStatus("CONFIRMED");
        }

        // Business Rule: Auto-reserve parking bay when selected
        if (booking.getParkingSlotId() != null) {
            parkingRepo.findById(booking.getParkingSlotId()).ifPresent(slot -> {
                slot.setStatus("RESERVED");
                parkingRepo.save(slot);
            });
        }

        return bookingRepo.save(booking);
    }

    @Transactional
    public Optional<Booking> updateBooking(Long id, Booking details) {
        return bookingRepo.findById(id).map(existing -> {
            existing.setCustomerName(details.getCustomerName());
            existing.setMovieTitle(details.getMovieTitle());
            existing.setHallName(details.getHallName());
            existing.setSeatNumbers(details.getSeatNumbers());
            existing.setTotalAmount(details.getTotalAmount());
            existing.setStatus(details.getStatus());
            // Sync new fields introduced for the interactive checkout
            existing.setParkingSlotId(details.getParkingSlotId());
            existing.setOrderedSnacks(details.getOrderedSnacks());
            existing.setDiscountAmount(details.getDiscountAmount());
            return bookingRepo.save(existing);
        });
    }

    @Transactional
    public boolean cancelBooking(Long id) {
        return bookingRepo.findById(id).map(booking -> {
            // Business Rule: Free up parking bay when booking is cancelled
            if (booking.getParkingSlotId() != null) {
                parkingRepo.findById(booking.getParkingSlotId()).ifPresent(slot -> {
                    slot.setStatus("AVAILABLE");
                    parkingRepo.save(slot);
                });
            }
            bookingRepo.deleteById(id);
            return true;
        }).orElse(false);
    }
}