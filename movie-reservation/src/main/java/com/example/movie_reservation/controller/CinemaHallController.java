package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.CinemaHall;
import com.example.movie_reservation.repository.CinemaHallRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/halls")
@CrossOrigin(origins = "*")
public class CinemaHallController {

    private final CinemaHallRepository repo;

    public CinemaHallController(CinemaHallRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<CinemaHall> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public CinemaHall create(@RequestBody CinemaHall hall) {
        return repo.save(hall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaHall> update(@PathVariable Long id, @RequestBody CinemaHall details) {
        return repo.findById(id).map(existing -> {
            existing.setHallName(details.getHallName());
            existing.setHallType(details.getHallType());
            existing.setTotalCapacity(details.getTotalCapacity());
            existing.setStatus(details.getStatus());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}