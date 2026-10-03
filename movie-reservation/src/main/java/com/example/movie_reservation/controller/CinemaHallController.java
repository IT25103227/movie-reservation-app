package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.CinemaHall;
import com.example.movie_reservation.service.CinemaHallService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/halls")
@CrossOrigin(origins = "*")
public class CinemaHallController {
    private final CinemaHallService service;

    public CinemaHallController(CinemaHallService service) { this.service = service; }

    @GetMapping
    public List<CinemaHall> getHalls() { return service.getAllHalls(); }

    @PostMapping
    public CinemaHall saveHall(@RequestBody CinemaHall hall) { return service.saveHall(hall); }

    @PutMapping("/{id}")
    public CinemaHall updateHall(@PathVariable Long id, @RequestBody CinemaHall hall) {
        hall.setId(id); // Locks the ID to overwrite the existing record
        return service.saveHall(hall);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id) { service.deleteHall(id); }
}