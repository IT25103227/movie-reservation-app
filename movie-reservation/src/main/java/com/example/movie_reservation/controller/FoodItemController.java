package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.FoodItem;
import com.example.movie_reservation.service.FoodItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@CrossOrigin(origins = "*")
public class FoodItemController {
    private final FoodItemService service;

    public FoodItemController(FoodItemService service) { this.service = service; }

    @GetMapping
    public List<FoodItem> getFood() { return service.getAllFood(); }

    @PostMapping
    public FoodItem saveFood(@RequestBody FoodItem food) { return service.saveFood(food); }

    @PutMapping("/{id}")
    public FoodItem updateFood(@PathVariable Long id, @RequestBody FoodItem food) {
        food.setId(id); // Locks the ID to overwrite the existing record
        return service.saveFood(food);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) { service.deleteFood(id); }
}