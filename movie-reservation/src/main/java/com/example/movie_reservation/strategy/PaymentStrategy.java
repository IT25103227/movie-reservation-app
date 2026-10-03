package com.example.movie_reservation.strategy;

/**
 * Strategy Interface — PaymentStrategy (Behavioral Design Pattern)
 *
 * Defines the contract that every concrete payment strategy must fulfill.
 * BookingService acts as the Context, selecting and executing the appropriate
 * strategy at runtime based on the customer's chosen payment method.
 */
public interface PaymentStrategy {

    /**
     * Process the payment for the given amount and customer.
     *
     * @param amount       the total amount to charge (in dollars)
     * @param customerName the name of the paying customer
     * @return true if payment succeeded, false otherwise
     */
    boolean processPayment(double amount, String customerName);

    /**
     * @return a short string key identifying this payment type,
     *         e.g. "CREDIT_CARD", "PAYPAL", "CASH"
     */
    String getPaymentType();
}
