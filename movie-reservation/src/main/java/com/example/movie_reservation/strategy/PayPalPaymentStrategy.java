package com.example.movie_reservation.strategy;

import org.springframework.stereotype.Component;

/**
 * Concrete Strategy — PayPal Payment
 *
 * Registered in the Spring context with bean name "PAYPAL".
 * BookingService resolves this strategy when paymentMethod == "PAYPAL".
 */
@Component("PAYPAL")
public class PayPalPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String customerName) {
        // In a real system this would call the PayPal REST API
        System.out.printf("[PaymentStrategy] PAYPAL: Processing $%.2f for '%s' — APPROVED%n",
                amount, customerName);
        return true;
    }

    @Override
    public String getPaymentType() {
        return "PAYPAL";
    }
}
