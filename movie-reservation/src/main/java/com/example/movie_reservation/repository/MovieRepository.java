// MovieRepository.java
package com.example.moviereservation.repository;
import com.example.moviereservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MovieRepository extends JpaRepository<Movie, Long> {}