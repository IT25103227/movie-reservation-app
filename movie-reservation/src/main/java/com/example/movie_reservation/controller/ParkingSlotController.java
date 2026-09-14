package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.ParkingSlot;
import com.example.movie_reservation.repository.ParkingSlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parking-slots")
@CrossOrigin(origins = "*")
public class ParkingSlotController {

    private final ParkingSlotRepository repo;

    public ParkingSlotController(ParkingSlotRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return repo.findAll();
    }

    @GetMapping("/available")
    public List<ParkingSlot> getAvailableSlots() {
        return repo.findByStatus("AVAILABLE");
    }

    @PostMapping
    public ParkingSlot createSlot(@RequestBody ParkingSlot slot) {
        if (slot.getStatus() == null) slot.setStatus("AVAILABLE");
        return repo.save(slot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(@PathVariable Long id, @RequestBody ParkingSlot details) {
        return repo.findById(id).map(existing -> {
            if (details.getSlotNumber() != null) existing.setSlotNumber(details.getSlotNumber());
            if (details.getSlotType() != null) existing.setSlotType(details.getSlotType());
            existing.setVehicleNumber(details.getVehicleNumber());
            if (details.getStatus() != null) existing.setStatus(details.getStatus());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}