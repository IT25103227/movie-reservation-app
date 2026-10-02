package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Booking> getBookings() {
        // Calls your existing getAllBookings() method
        return service.getAllBookings();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        // Calls your existing processBooking() method
        return service.processBooking(booking);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        // Calls your existing cancelBooking() method
        service.cancelBooking(id);
    }
}