// BookingRepository.java
package com.example.moviereservation.repository;
import com.example.moviereservation.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking, Long> {}