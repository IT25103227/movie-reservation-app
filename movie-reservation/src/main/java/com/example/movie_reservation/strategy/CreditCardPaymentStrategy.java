package com.example.movie_reservation.strategy;

import org.springframework.stereotype.Component;

/**
 * Concrete Strategy — Credit / Debit Card Payment
 *
 * Registered in the Spring context with bean name "CREDIT_CARD".
 * BookingService resolves this strategy when paymentMethod == "CREDIT_CARD".
 */
@Component("CREDIT_CARD")
public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String customerName) {
        // In a real system this would call a payment gateway (Stripe, etc.)
        System.out.printf("[PaymentStrategy] CREDIT_CARD: Processing $%.2f for '%s' — APPROVED%n",
                amount, customerName);
        return true;
    }

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }
}
