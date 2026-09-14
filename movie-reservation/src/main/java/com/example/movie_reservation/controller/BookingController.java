// BookingController.java
package com.example.moviereservation.controller;

import com.example.moviereservation.model.Booking;
import com.example.moviereservation.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    private final BookingRepository repo;
    public BookingController(BookingRepository repo) { this.repo = repo; }

    @GetMapping public List<Booking> getAll() { return repo.findAll(); }
    @PostMapping public Booking create(@RequestBody Booking item) {
        if (item.getStatus() == null) item.setStatus("CONFIRMED");
        return repo.save(item);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking details) {
        return repo.findById(id).map(existing -> {
            existing.setCustomerName(details.getCustomerName());
            existing.setMovieTitle(details.getMovieTitle());
            existing.setHallName(details.getHallName());
            existing.setSeatNumbers(details.getSeatNumbers());
            existing.setTotalAmount(details.getTotalAmount());
            existing.setStatus(details.getStatus());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.noContent().build(); }
        return ResponseEntity.notFound().build();
    }
}