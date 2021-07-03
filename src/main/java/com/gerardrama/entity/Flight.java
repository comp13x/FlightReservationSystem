package com.gerardrama.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Flight {
    @Id
    @SequenceGenerator( name = "flight_sequence",
            sequenceName = "flight_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "flight_sequence")
    @Column(name = "flight_id", updatable = false)
    private Integer flightId;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "from_flight")
    private String fromFlight;

    @Column(name = "to_flight")
    private String toFlight;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    @JsonIgnoreProperties(value = "flightList")
    private Trip trip;

    public Flight(String fromFlight, String toFlight, LocalDate departureDate, LocalDate arrivalDate) {
        this.fromFlight = fromFlight;
        this.toFlight = toFlight;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public Flight() {
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getToFlight() {
        return toFlight;
    }

    public void setToFlight(String toFlight) {
        this.toFlight = toFlight;
    }

    public String getFromFlight() {
        return fromFlight;
    }

    public void setFromFlight(String fromFlight) {
        this.fromFlight = fromFlight;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", toFlight='" + toFlight + '\'' +
                ", fromFlight='" + fromFlight + '\'' +
                '}';
    }
}
