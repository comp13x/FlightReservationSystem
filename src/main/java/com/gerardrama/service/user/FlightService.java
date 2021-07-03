package com.gerardrama.service.user;

import com.gerardrama.entity.Flight;
import com.gerardrama.entity.form.FlightCreateForm;
import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights(Integer id);

    Flight create(FlightCreateForm form, Integer tripId);

    Flight getFlightById(Integer id);
}
