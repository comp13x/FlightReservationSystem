package com.gerardrama.service.user;

import com.gerardrama.entity.Trip;
import com.gerardrama.entity.form.TripCreateForm;
import java.util.Set;

public interface TripService {
    Trip getTripById(Integer id);

    Trip create(TripCreateForm form, long userId);

    Set<Trip> getAllTrips(long id);

    Trip approveStatus(Integer tripId);

    Trip denyStatus(Integer tripId);

    Trip applyStatus(Integer tripId);

    Object deleteTrip(Integer tripId);

    Trip updateTrip(Integer tripId, TripCreateForm form);

    Trip addFlight(Integer tripId, Integer flightId);
}
