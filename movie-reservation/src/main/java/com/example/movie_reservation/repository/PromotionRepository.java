package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {}
