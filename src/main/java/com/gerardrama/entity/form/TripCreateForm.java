package com.gerardrama.entity.form;

import com.gerardrama.entity.enums.Reason;
import com.gerardrama.entity.User;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public class TripCreateForm {
    private Set<User> users;

    @NotNull
    private Reason reason = Reason.Other;

    private String description = "";

    @NotEmpty
    private String fromTrip = "";

    @NotEmpty
    private String toTrip = "";

    @NotNull
    private LocalDate departureDate = LocalDate.now();

    @NotNull
    private LocalDate arrivalDate = LocalDate.now();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
        return "TripCreateForm{" +
                "reason=" + reason +
                ", description='" + description + '\'' +
                ", fromTrip='" + fromTrip + '\'' +
                ", toTrip='" + toTrip + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
