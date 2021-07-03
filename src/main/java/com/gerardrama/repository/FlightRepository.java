package com.gerardrama.repository;

import com.gerardrama.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findFlightsByArrivalDate(LocalDate date);
    List<Flight> findFlightsByDepartureDate(LocalDate date);
    List<Flight> findFlightsByFromFlight(String fromFlight);
    List<Flight> findFlightsByToFlight(String toFlight);
}
