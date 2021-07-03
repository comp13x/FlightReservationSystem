package com.gerardrama.repository;

import com.gerardrama.entity.enums.Approval;
import com.gerardrama.entity.enums.Reason;
import com.gerardrama.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    Set<Trip> findTripsByApproval(Approval approval);
    Set<Trip> findTripsByArrivalDate(LocalDate arrivalDate);
    Set<Trip> findTripsByDepartureDate(LocalDate departureDate);
    Set<Trip> findTripsByReason(Reason reason);
}
