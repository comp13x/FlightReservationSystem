package com.gerardrama.service.currentuser;

import com.gerardrama.controller.UsersController;
import com.gerardrama.entity.CurrentUser;
import com.gerardrama.entity.enums.Role;
import com.gerardrama.entity.Trip;
import com.gerardrama.entity.User;
import com.gerardrama.repository.TripRepository;
import com.gerardrama.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private static final Logger LOGGER = LogManager.getLogger(CurrentUserServiceImpl.class);

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public CurrentUserServiceImpl(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        LOGGER.info("Checking if user={} has access to user={}", currentUser, userId);
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }

    @Override
    public boolean canAccessFlights(CurrentUser currentUser, Long userId, Integer tripId) {
        LOGGER.info("Checking if user={} has access to trip's={} flights", currentUser, tripId);
        if(currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId))) {
            Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                    "Trip with id " + " not found"
            ));
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                    "User with id " + " not found"
            ));
            if(user.getTrips().contains(trip)){
                return trip.getApproval().equals("APPROVED");
            }
        }
        return false;
    }

    @Override
    public boolean canChangeStatus(CurrentUser currentUser, Long userId, Integer tripId) {
        LOGGER.info("Checking if user={} can change status of trip={}", currentUser, tripId);
        if(currentUser != null && (currentUser.getRole() == Role.ADMIN)) {
            Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                    "Trip with id " + " not found"
            ));
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                    "User with id " + " not found"
            ));
            if(user.getTrips().contains(trip)){
                return trip.getApproval().equals("WAITING FOR APPROVAL");
            }
        }
        return false;
    }

    @Override
    public boolean canSubmitStatus(CurrentUser currentUser, Long userId, Integer tripId) {
        LOGGER.info("Checking if user={} can submit status of trip={}", currentUser, tripId);
        if(currentUser != null && (currentUser.getId().equals(userId))) {
            Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                    "Trip with id " + " not found"
            ));
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                    "User with id " + " not found"
            ));
            if(user.getTrips().contains(trip)){
                return trip.getApproval().equals("CREATED");
            }
        }
        return false;
    }

    @Override
    public boolean canEditDeleteTrip(CurrentUser currentUser, Long userId, Integer tripId) {
        LOGGER.info("Checking if user={} can delete trip={}", currentUser, tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id " + " not found"
        ));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                "User with id " + " not found"
        ));
        if(user.getTrips().contains(trip)){
            if(trip.getApproval().equals("CREATED")){
                return currentUser.getId().equals(userId);
            }
            else if(!trip.getApproval().equals("CREATED")) {
                return currentUser != null
                        && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
            }
        }
        return false;
    }
}
