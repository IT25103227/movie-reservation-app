package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {}