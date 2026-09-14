// MovieController.java
package com.example.moviereservation.controller;

import com.example.moviereservation.model.Movie;
import com.example.moviereservation.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieRepository repo;
    public MovieController(MovieRepository repo) { this.repo = repo; }

    @GetMapping public List<Movie> getAll() { return repo.findAll(); }
    @PostMapping public Movie create(@RequestBody Movie item) { return repo.save(item); }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie details) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(details.getTitle());
            existing.setGenre(details.getGenre());
            existing.setDurationMinutes(details.getDurationMinutes());
            existing.setShowtime(details.getShowtime());
            existing.setTicketPrice(details.getTicketPrice());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.noContent().build(); }
        return ResponseEntity.notFound().build();
    }
}