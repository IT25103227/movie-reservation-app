package com.example.movie_reservation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String movieTitle;
    private String hallName;
    private String seatNumbers;
    private Double totalAmount;
    private String status;

    /** ID of the ParkingSlot reserved with this booking (nullable). */
    private Long parkingSlotId;

    /** JSON array string of ordered snacks, e.g. [{name,qty,price}, ...] */
    @Column(columnDefinition = "TEXT")
    private String orderedSnacks;

    /** Dollar amount discounted via promo code (0 if none applied). */
    private Double discountAmount;

    public Booking() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }

    public String getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getParkingSlotId() { return parkingSlotId; }
    public void setParkingSlotId(Long parkingSlotId) { this.parkingSlotId = parkingSlotId; }

    public String getOrderedSnacks() { return orderedSnacks; }
    public void setOrderedSnacks(String orderedSnacks) { this.orderedSnacks = orderedSnacks; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
}