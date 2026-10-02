package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) { this.repository = repository; }

    public List<Movie> getAllMovies() { return repository.findAll(); }
    public Movie saveMovie(Movie movie) { return repository.save(movie); }
    public void deleteMovie(Long id) { repository.deleteById(id); }
}