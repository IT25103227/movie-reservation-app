package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.service.MovieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) { this.service = service; }

    @GetMapping
    public List<Movie> getMovies() { return service.getAllMovies(); }

    @PostMapping
    public Movie saveMovie(@RequestBody Movie movie) { return service.saveMovie(movie); }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id); // Ensure the ID matches the existing record
        return service.saveMovie(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) { service.deleteMovie(id); }
}