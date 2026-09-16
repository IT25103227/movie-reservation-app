package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}