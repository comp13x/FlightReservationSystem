package com.gerardrama.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gerardrama.entity.enums.Approval;
import com.gerardrama.entity.enums.Reason;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Trip {
    @Id
    @SequenceGenerator( name = "trip_sequence",
            sequenceName = "trip_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "trip_sequence")
    @Column(name = "trip_id", updatable = false, insertable = false)
    private Integer tripId;

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Column(name = "description")
    private String description;

    @Column(name = "approval")
    @Enumerated(EnumType.STRING)
    private Approval approval;

    @Column(name = "from_trip")
    private String fromTrip;

    @Column(name = "to_trip")
    private String toTrip;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    public Trip() {
    }

    public Trip(Reason reason,
                String description,
                Approval approval,
                String fromTrip,
                String toTrip,
                LocalDate departureDate,
                LocalDate arrivalDate) {
        this.reason = reason;
        this.description = description;
        this.approval = approval;
        this.fromTrip = fromTrip;
        this.toTrip = toTrip;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("trip")
    private List<Flight> flightList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH}, mappedBy = "trips"
    )
    @JsonIgnoreProperties("trips")
    private Set<User> users = new HashSet<>();

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Approval isApproval() {
        return approval;
    }

    public String getApproval() {
        return String.valueOf(approval);
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public String getFromTrip() {
        return fromTrip;
    }

    public void setFromTrip(String fromTrip) {
        this.fromTrip = fromTrip;
    }

    public String getToTrip() {
        return toTrip;
    }

    public void setToTrip(String toTrip) {
        this.toTrip = toTrip;
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



    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", reason=" + reason +
                ", description='" + description + '\'' +
                ", approval=" + approval +
                ", fromTrip='" + fromTrip + '\'' +
                ", toTrip='" + toTrip + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
