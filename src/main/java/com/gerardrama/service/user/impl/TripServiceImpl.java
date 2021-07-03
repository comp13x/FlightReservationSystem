package com.gerardrama.service.user.impl;

import com.gerardrama.controller.UsersController;
import com.gerardrama.entity.*;
import com.gerardrama.entity.enums.Approval;
import com.gerardrama.repository.FlightRepository;
import com.gerardrama.repository.UserRepository;
import com.gerardrama.service.user.TripService;
import com.gerardrama.entity.form.TripCreateForm;
import com.gerardrama.repository.TripRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TripServiceImpl implements TripService {
    private static final Logger LOGGER = LogManager.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Trip getTripById(Integer id) {
        LOGGER.info("Getting trip={}", id);
        return tripRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + id + " not found"
        ));
    }

    @Override
    public Set<Trip> getAllTrips(long id) {
        LOGGER.info("Getting all trips of user={}", id);
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with id " + id + " not found"
        )).getTrips();
    }

    @Override
    public Trip addFlight(Integer tripId, Integer flightId){
        LOGGER.info("Adding flight={} to trip={}", flightId, tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id: " + tripId + " does not exist."
        ));
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new IllegalStateException(
                "Flight with id: " + flightId + " does not exist"
        ));
        trip.getFlightList().add(flight);
        flight.setTrip(trip);
        flightRepository.save(flight);
        return tripRepository.save(trip);
    }

    @Override
    public Trip create(TripCreateForm form, long userId) {
        LOGGER.info("Creating trip for user={}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                "User with id" + userId + "does not exist"
        ));
        Set<User> users = Collections.singleton(user);
        Trip trip = new Trip();
        trip.setToTrip(form.getToTrip());
        trip.setFromTrip(form.getFromTrip());
        trip.setDescription(form.getDescription());
        trip.setDepartureDate(form.getDepartureDate());
        trip.setArrivalDate(form.getArrivalDate());
        trip.setReason(form.getReason());
        trip.setApproval(Approval.CREATED);
        trip.setUsers(users);
        user.getTrips().add(trip);
        return tripRepository.save(trip);
    }

    @Override
    public Trip approveStatus(Integer tripId) {
        LOGGER.info("Approving status of trip={}", tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + tripId + " not found"
        ));;
        trip.setApproval(Approval.APPROVED);
        return tripRepository.save(trip);
    }

    @Override
    public Trip denyStatus(Integer tripId) {
        LOGGER.info("Denying status of trip={}", tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + tripId + " not found"
        ));
        trip.setApproval(Approval.DENIED);
        return tripRepository.save(trip);
    }

    @Override
    public Trip applyStatus(Integer tripId) {
        LOGGER.info("Submitting status of trip={}", tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + tripId + " not found"
        ));
        trip.setApproval(Approval.WAITING);
        return tripRepository.save(trip);
    }

    @Override
    public Object deleteTrip(Integer tripId) {
        LOGGER.info("Deleting trip={}", tripId);
        boolean exists = tripRepository.existsById(tripId);
        if(!exists) {
            throw new IllegalStateException("Trip with id " + tripId + " does not exist");
        }
        Trip trip = tripRepository.getById(tripId);
        for(User user: trip.getUsers()){
            user.getTrips().remove(trip);
        }
        tripRepository.deleteById(tripId);
        return null;
    }

    @Override
    public Trip updateTrip(Integer tripId, TripCreateForm form) {
        LOGGER.info("Updating trip={}", tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + tripId + " not found"
        ));
        trip.setToTrip(form.getToTrip());
        trip.setFromTrip(form.getFromTrip());
        trip.setDescription(form.getDescription());
        trip.setDepartureDate(form.getDepartureDate());
        trip.setArrivalDate(form.getArrivalDate());
        trip.setReason(form.getReason());
        trip.setApproval(Approval.CREATED);
        return tripRepository.save(trip);
    }
}
