package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /** Case-insensitive lookup used by the promo-validation endpoint. */
    Optional<Promotion> findByPromoCodeIgnoreCase(String promoCode);
}
