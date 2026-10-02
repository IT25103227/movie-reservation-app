package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Promotion;
import com.example.movie_reservation.service.PromotionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*")
public class PromotionController {
    private final PromotionService service;

    public PromotionController(PromotionService service) { this.service = service; }

    @GetMapping
    public List<Promotion> getPromotions() { return service.getAllPromotions(); }

    @PostMapping
    public Promotion savePromotion(@RequestBody Promotion promotion) { return service.savePromotion(promotion); }

    @PutMapping("/{id}")
    public Promotion updatePromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
        promotion.setId(id); // Locks the ID to overwrite the existing record
        return service.savePromotion(promotion);
    }

    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable Long id) { service.deletePromotion(id); }
}