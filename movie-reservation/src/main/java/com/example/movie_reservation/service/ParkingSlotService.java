package com.example.movie_reservation.service;

import com.example.movie_reservation.model.ParkingSlot;
import com.example.movie_reservation.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParkingSlotService {
    private final ParkingSlotRepository repository;

    public ParkingSlotService(ParkingSlotRepository repository) { this.repository = repository; }

    public List<ParkingSlot> getAllSlots() { return repository.findAll(); }
    public ParkingSlot saveSlot(ParkingSlot slot) { return repository.save(slot); }
    public void deleteSlot(Long id) { repository.deleteById(id); }
}