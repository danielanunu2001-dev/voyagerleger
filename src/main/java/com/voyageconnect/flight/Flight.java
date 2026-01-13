package com.voyageconnect.flight;

import com.voyageconnect.destination.Destination;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * Represents a flight.
 */
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String airline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_destination_id", nullable = false)
    private Destination departureDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_destination_id", nullable = false)
    private Destination arrivalDestination;

    @Column(name = "depart_at", nullable = false)
    private OffsetDateTime departAt;

    @Column(name = "arrive_at", nullable = false)
    private OffsetDateTime arriveAt;

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

    public Destination getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(Destination departureDestination) {
        this.departureDestination = departureDestination;
    }

    public Destination getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(Destination arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public OffsetDateTime getDepartAt() {
        return departAt;
    }

    public void setDepartAt(OffsetDateTime departAt) {
        this.departAt = departAt;
    }

    public OffsetDateTime getArriveAt() {
        return arriveAt;
    }

    public void setArriveAt(OffsetDateTime arriveAt) {
        this.arriveAt = arriveAt;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }
}
