package com.voyageconnect.flight;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airline;

    @Column(name = "depart_at", nullable = false)
    private OffsetDateTime depart;

    @Column(name = "arrive_at", nullable = false)
    private OffsetDateTime arrive;

    @Column(name = "seats_available")
    private Integer seatsAvailable;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public OffsetDateTime getDepart() {
        return depart;
    }

    public void setDepart(OffsetDateTime depart) {
        this.depart = depart;
    }

    public OffsetDateTime getArrive() {
        return arrive;
    }

    public void setArrive(OffsetDateTime arrive) {
        this.arrive = arrive;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }
}
