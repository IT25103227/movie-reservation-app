package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Promotion;
import com.example.movie_reservation.repository.PromotionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromotionService {
    private final PromotionRepository repository;

    public PromotionService(PromotionRepository repository) { this.repository = repository; }

    public List<Promotion> getAllPromotions() { return repository.findAll(); }
    public Promotion savePromotion(Promotion promotion) { return repository.save(promotion); }
    public void deletePromotion(Long id) { repository.deleteById(id); }
}