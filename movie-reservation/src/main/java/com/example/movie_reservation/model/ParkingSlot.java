package com.example.movie_reservation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slotNumber;
    private String slotType; // Regular, VIP, Disabled
    private String vehicleNumber;
    private String status; // AVAILABLE, RESERVED, OCCUPIED

    public ParkingSlot() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public String getSlotType() { return slotType; }
    public void setSlotType(String slotType) { this.slotType = slotType; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}