package com.example.movie_reservation.service;

import com.example.movie_reservation.model.FoodItem;
import com.example.movie_reservation.repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodItemService {
    private final FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) { this.repository = repository; }

    public List<FoodItem> getAllFood() { return repository.findAll(); }
    public FoodItem saveFood(FoodItem food) { return repository.save(food); }
    public void deleteFood(Long id) { repository.deleteById(id); }
}