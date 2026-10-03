package com.example.movie_reservation.strategy;

import org.springframework.stereotype.Component;

/**
 * Concrete Strategy — Cash at Box Office Payment
 *
 * Registered in the Spring context with bean name "CASH".
 * This is the default strategy when no paymentMethod is specified.
 * No gateway call needed — simply marks the payment as pending collection.
 */
@Component("CASH")
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(double amount, String customerName) {
        // Cash is always "accepted" at booking time; collection happens at the box office
        System.out.printf("[PaymentStrategy] CASH: $%.2f reserved for '%s' — PENDING COLLECTION%n",
                amount, customerName);
        return true;
    }

    @Override
    public String getPaymentType() {
        return "CASH";
    }
}
