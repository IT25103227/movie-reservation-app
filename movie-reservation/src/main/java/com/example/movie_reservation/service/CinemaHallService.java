package com.example.movie_reservation.service;

import com.example.movie_reservation.model.CinemaHall;
import com.example.movie_reservation.repository.CinemaHallRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CinemaHallService {
    private final CinemaHallRepository repository;

    public CinemaHallService(CinemaHallRepository repository) { this.repository = repository; }

    public List<CinemaHall> getAllHalls() { return repository.findAll(); }
    public CinemaHall saveHall(CinemaHall hall) { return repository.save(hall); }
    public void deleteHall(Long id) { repository.deleteById(id); }
}