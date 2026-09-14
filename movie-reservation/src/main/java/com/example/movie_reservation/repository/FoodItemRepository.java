package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {}

