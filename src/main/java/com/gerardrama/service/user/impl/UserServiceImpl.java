package com.gerardrama.service.user.impl;

import com.gerardrama.entity.User;
import com.gerardrama.entity.form.UserCreateForm;
import com.gerardrama.service.user.UserService;
import com.gerardrama.entity.Trip;
import com.gerardrama.repository.TripRepository;
import com.gerardrama.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public User getUserById(long id) {
        LOGGER.info("Getting user={}", id);
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with id " + id + " not found"
        ));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.info("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Getting all users");
        return userRepository.findAll(Sort.by("email"));
    }

    @Override
    public User addTrip(long userId, Integer tripId){
        LOGGER.info("Adding trip={} to user={}", tripId, userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                "User with id: " + userId + " does not exist."
        ));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalStateException(
                "Trip with id: " + tripId + " does not exist"
        ));
        user.getTrips().add(trip);
        return userRepository.save(user);
    }

    @Override
    public User create(UserCreateForm form) {
        LOGGER.info("Creating user...");
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setDateOfBirth(form.getDateOfBirth());
        user.setPhone(form.getPhone());
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }
}
