package com.gerardrama.config;

import com.gerardrama.entity.*;
import com.gerardrama.entity.enums.Approval;
import com.gerardrama.entity.enums.Reason;
import com.gerardrama.entity.enums.Role;
import com.gerardrama.repository.FlightRepository;
import com.gerardrama.repository.TripRepository;
import com.gerardrama.repository.UserRepository;
import com.gerardrama.service.user.impl.TripServiceImpl;
import com.gerardrama.service.user.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            TripRepository tripRepository,
            UserServiceImpl userService,
            TripServiceImpl tripService,
            FlightRepository flightRepository){
        return args -> {
            Trip trip = new Trip(Reason.Project,
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio. Quisque volutpat mattis eros. Nullam malesuada erat ut turpis. Suspendisse urna nibh, viverra non, semper suscipit, posuere a, pede.",
                    Approval.WAITING,
                    "Albania",
                    "Germany",
                    LocalDate.of(2021, 6, 20),
                    LocalDate.of(2021, 7, 10));
            Trip trip2 = new Trip(Reason.Event,
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec odio. Quisque volutpat mattis eros. Nullam malesuada erat ut turpis.",
                    Approval.APPROVED,
                    "Greece",
                    "England",
                    LocalDate.of(2021, 7, 1),
                    LocalDate.of(2021, 8, 1));
            Trip trip3 = new Trip(Reason.Meeting,
                    "Lorem ipsum dolor.",
                    Approval.DENIED,
                    "New York",
                    "Ireland",
                    LocalDate.of(2021, 8, 10),
                    LocalDate.of(2021, 8, 20));
            Trip trip4 = new Trip(Reason.Training,
                    "Training training training training training training training training training training.",
                    Approval.APPROVED,
                    "France",
                    "Albania",
                    LocalDate.of(2021, 9, 15),
                    LocalDate.of(2021, 9, 20));

            tripRepository.save(trip);
            tripRepository.save(trip2);
            tripRepository.save(trip3);
            tripRepository.save(trip4);

            Flight flight = new Flight("Albania",
                                        "Germany",
                                            LocalDate.of(2021, 6, 20),
                                            LocalDate.of(2021, 7, 10));
            Flight flight2 = new Flight("Switzerland",
                    "Monaco",
                    LocalDate.of(2021, 7, 10),
                    LocalDate.of(2021, 7, 30));
            Flight flight3 = new Flight("Luxembourg",
                    "Norway",
                    LocalDate.of(2021, 7, 1),
                    LocalDate.of(2021, 8, 3));

            flightRepository.save(flight);
            flightRepository.save(flight2);
            flightRepository.save(flight3);

            User adminUser = new User("Gerard",
                    "Rama",
                    "0682045738",
                    LocalDate.of(2001, 4, 2),
                    "gerardrama10@gmail.com",
                    "$2y$12$pDV7FpfcumNeKTOywPOaC.IutfrOW7876r9sD0Sj6HdDHyj5G1mha",
                    Role.ADMIN);
            User adminUser2 = new User("Admin",
                    "User",
                    "0682041111",
                    LocalDate.of(1998, 6, 20),
                    "admin@gmail.com",
                    "$2y$12$pDV7FpfcumNeKTOywPOaC.IutfrOW7876r9sD0Sj6HdDHyj5G1mha",
                    Role.ADMIN);

            User lowAccessUser = new User("John",
                    "Doe",
                    "0691234567",
                    LocalDate.of(1990, 11, 15),
                    "johndoe@outlook.com",
                    "$2y$12$pDV7FpfcumNeKTOywPOaC.IutfrOW7876r9sD0Sj6HdDHyj5G1mha",
                    Role.USER);
            User lowAccessUser2 = new User("Jane",
                    "Doe",
                    "0687654321",
                    LocalDate.of(1990, 1, 19),
                    "janedoe@yahoo.com",
                    "$2y$12$pDV7FpfcumNeKTOywPOaC.IutfrOW7876r9sD0Sj6HdDHyj5G1mha",
                    Role.USER);

            userRepository.save(adminUser);
            userRepository.save(adminUser2);
            userRepository.save(lowAccessUser);
            userRepository.save(lowAccessUser2);

            userService.addTrip(adminUser.getId(), trip.getTripId());
            userService.addTrip(adminUser.getId(), trip2.getTripId());
            userService.addTrip(adminUser2.getId(), trip3.getTripId());
            userService.addTrip(lowAccessUser.getId(), trip4.getTripId());

            tripService.addFlight(trip.getTripId(), flight.getFlightId());
            tripService.addFlight(trip.getTripId(), flight2.getFlightId());
            tripService.addFlight(trip4.getTripId(), flight3.getFlightId());
        };
    }
}

