package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieRepository repo;

    public MovieController(MovieRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Movie> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return repo.save(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie details) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(details.getTitle());
            existing.setGenre(details.getGenre());
            existing.setDurationMinutes(details.getDurationMinutes());
            existing.setShowtime(details.getShowtime());
            existing.setTicketPrice(details.getTicketPrice());
            existing.setPosterUrl(details.getPosterUrl());
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