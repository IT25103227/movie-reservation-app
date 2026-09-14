package com.example.movie_reservation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hallName;
    private String hallType; // IMAX, Standard, 3D
    private Integer totalCapacity;
    private String status; // ACTIVE, MAINTENANCE

    public CinemaHall() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public String getHallType() { return hallType; }
    public void setHallType(String hallType) { this.hallType = hallType; }
    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
