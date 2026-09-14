package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByStatus(String status);
}