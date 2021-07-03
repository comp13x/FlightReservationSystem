package com.gerardrama.entity.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FlightCreateForm {
    @NotNull
    private LocalDate departureDate = LocalDate.now();

    @NotNull
    private LocalDate arrivalDate = LocalDate.now();

    @NotEmpty
    private String fromFlight = "";

    @NotEmpty
    private String toFlight = "";

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

    public String getFromFlight() {
        return fromFlight;
    }

    public void setFromFlight(String fromFlight) {
        this.fromFlight = fromFlight;
    }

    public String getToFlight() {
        return toFlight;
    }

    public void setToFlight(String toFlight) {
        this.toFlight = toFlight;
    }

    @Override
    public String toString() {
        return "FlightCreateForm{" +
                "departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", fromFlight='" + fromFlight + '\'' +
                ", toFlight='" + toFlight + '\'' +
                '}';
    }
}
