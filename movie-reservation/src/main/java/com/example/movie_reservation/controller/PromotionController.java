package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Promotion;
import com.example.movie_reservation.repository.PromotionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*")
public class PromotionController {

    private final PromotionRepository repo;

    public PromotionController(PromotionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Promotion> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Promotion create(@RequestBody Promotion promo) {
        return repo.save(promo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> update(@PathVariable Long id, @RequestBody Promotion details) {
        return repo.findById(id).map(existing -> {
            existing.setPromoCode(details.getPromoCode());
            existing.setDiscountPercentage(details.getDiscountPercentage());
            existing.setDescription(details.getDescription());
            existing.setActive(details.getActive());
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