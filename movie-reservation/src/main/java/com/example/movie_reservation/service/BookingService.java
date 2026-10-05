package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.CinemaHallRepository;
import com.example.movie_reservation.repository.ParkingSlotRepository;
import com.example.movie_reservation.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * BookingService — Context class in the Strategy Design Pattern.
 *
 * Spring automatically builds a Map<String, PaymentStrategy> whose keys are the
 * @Component bean names ("CREDIT_CARD", "PAYPAL", "CASH") and whose values are
 * the corresponding concrete strategy instances.  processBooking() resolves the
 * correct strategy at runtime based on booking.getPaymentMethod() and delegates
 * payment execution to it — without any if/else or switch on type names.
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final ParkingSlotRepository parkingRepo;
    private final CinemaHallRepository hallRepo;

    /**
     * Spring injects all PaymentStrategy beans into this map automatically.
     * Key   = @Component bean name (e.g. "CREDIT_CARD")
     * Value = concrete strategy instance
     */
    private final Map<String, PaymentStrategy> paymentStrategies;

    public BookingService(BookingRepository bookingRepo,
                          ParkingSlotRepository parkingRepo,
                          CinemaHallRepository hallRepo,
                          Map<String, PaymentStrategy> paymentStrategies) {
        this.bookingRepo       = bookingRepo;
        this.parkingRepo       = parkingRepo;
        this.hallRepo          = hallRepo;
        this.paymentStrategies = paymentStrategies;
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    @Transactional
    public Booking processBooking(Booking booking) {
        // ── Guard: hall must be supplied and must exist in the DB ──
        if (booking.getHallName() == null || booking.getHallName().isBlank()) {
            throw new IllegalArgumentException("A valid cinema hall must be selected to complete a booking.");
        }
        boolean hallExists = hallRepo.findAll().stream()
                .anyMatch(h -> h.getHallName().equalsIgnoreCase(booking.getHallName().trim()));
        if (!hallExists) {
            throw new IllegalArgumentException(
                "Cinema hall '" + booking.getHallName() + "' does not exist or is unavailable.");
        }

        if (booking.getStatus() == null) {
            booking.setStatus("CONFIRMED");
        }

        // ── Strategy Pattern: resolve and execute the payment strategy ──
        String methodKey = (booking.getPaymentMethod() != null && !booking.getPaymentMethod().isBlank())
                ? booking.getPaymentMethod().toUpperCase()
                : "CASH";                                // default to cash if not specified

        PaymentStrategy strategy = paymentStrategies.getOrDefault(methodKey, paymentStrategies.get("CASH"));
        booking.setPaymentMethod(strategy.getPaymentType()); // normalise stored value

        double amount = (booking.getTotalAmount() != null) ? booking.getTotalAmount() : 0.0;
        boolean paymentSuccess = strategy.processPayment(amount, booking.getCustomerName());

        booking.setPaymentStatus(paymentSuccess ? "PAID" : "FAILED");

        // Override booking status if payment failed
        if (!paymentSuccess) {
            booking.setStatus("PAYMENT_FAILED");
        }

        // ── Business Rule: Auto-reserve parking bay when selected ──
        if (booking.getParkingSlotId() != null) {
            parkingRepo.findById(booking.getParkingSlotId()).ifPresent(slot -> {
                slot.setStatus("RESERVED");
                parkingRepo.save(slot);
            });
        }

        return bookingRepo.save(booking);
    }

    @Transactional
    public Optional<Booking> updateBooking(Long id, Booking details) {
        return bookingRepo.findById(id).map(existing -> {
            existing.setCustomerName(details.getCustomerName());
            existing.setMovieTitle(details.getMovieTitle());
            existing.setHallName(details.getHallName());
            existing.setSeatNumbers(details.getSeatNumbers());
            existing.setTotalAmount(details.getTotalAmount());
            existing.setStatus(details.getStatus());
            // Sync checkout fields
            existing.setParkingSlotId(details.getParkingSlotId());
            existing.setOrderedSnacks(details.getOrderedSnacks());
            existing.setDiscountAmount(details.getDiscountAmount());
            existing.setPaymentMethod(details.getPaymentMethod());
            existing.setPaymentStatus(details.getPaymentStatus());
            return bookingRepo.save(existing);
        });
    }

    @Transactional
    public boolean cancelBooking(Long id) {
        return bookingRepo.findById(id).map(booking -> {
            // Business Rule: Free up parking bay when booking is cancelled
            if (booking.getParkingSlotId() != null) {
                parkingRepo.findById(booking.getParkingSlotId()).ifPresent(slot -> {
                    slot.setStatus("AVAILABLE");
                    parkingRepo.save(slot);
                });
            }
            bookingRepo.deleteById(id);
            return true;
        }).orElse(false);
    }
}