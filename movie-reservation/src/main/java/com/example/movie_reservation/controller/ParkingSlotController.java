package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.ParkingSlot;
import com.example.movie_reservation.service.ParkingSlotService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parking-slots")
@CrossOrigin(origins = "*")
public class ParkingSlotController {
    private final ParkingSlotService service;

    public ParkingSlotController(ParkingSlotService service) { this.service = service; }

    @GetMapping
    public List<ParkingSlot> getSlots() { return service.getAllSlots(); }

    @PostMapping
    public ParkingSlot saveSlot(@RequestBody ParkingSlot slot) { return service.saveSlot(slot); }

    @PutMapping("/{id}")
    public ParkingSlot updateSlot(@PathVariable Long id, @RequestBody ParkingSlot slot) {
        slot.setId(id);
        return service.saveSlot(slot);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable Long id) { service.deleteSlot(id); }
}