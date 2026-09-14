package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.FoodItem;
import com.example.movie_reservation.repository.FoodItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@CrossOrigin(origins = "*")
public class FoodItemController {

    private final FoodItemRepository repo;

    public FoodItemController(FoodItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<FoodItem> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public FoodItem create(@RequestBody FoodItem food) {
        return repo.save(food);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> update(@PathVariable Long id, @RequestBody FoodItem details) {
        return repo.findById(id).map(existing -> {
            existing.setItemName(details.getItemName());
            existing.setCategory(details.getCategory());
            existing.setPrice(details.getPrice());
            existing.setIsAvailable(details.getIsAvailable());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
