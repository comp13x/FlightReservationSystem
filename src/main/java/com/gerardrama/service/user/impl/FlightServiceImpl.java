package com.gerardrama.service.user.impl;

import com.gerardrama.controller.UsersController;
import com.gerardrama.entity.Flight;
import com.gerardrama.entity.Trip;
import com.gerardrama.repository.TripRepository;
import com.gerardrama.service.user.FlightService;
import com.gerardrama.entity.form.FlightCreateForm;
import com.gerardrama.repository.FlightRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {
    private static final Logger LOGGER = LogManager.getLogger(FlightServiceImpl.class);

    private final FlightRepository flightRepository;
    private final TripRepository tripRepository ;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, TripRepository tripRepository) {
        this.flightRepository = flightRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Flight getFlightById(Integer id) {
        LOGGER.info("Getting flight={}", id);
        return flightRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Flight with id " + id + " not found"
        ));
    }

    @Override
    public List<Flight> getAllFlights(Integer tripId) {
        LOGGER.info("Getting all flights of trip={}", tripId);
        return tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + " not found"
        )).getFlightList();
    }

    @Override
    public Flight create(FlightCreateForm form, Integer tripId) {
        LOGGER.info("Creating flight for trip={}", tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + tripId + "not found"
        ));
        Flight flight = new Flight();
        flight.setFromFlight(form.getFromFlight());
        flight.setToFlight(form.getToFlight());
        flight.setArrivalDate(form.getArrivalDate());
        flight.setDepartureDate(form.getDepartureDate());
        flight.setTrip(trip);
        trip.getFlightList().add(flight);
        return flightRepository.save(flight);
    }
}
