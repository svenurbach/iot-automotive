package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Trip} entities.
 */
@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

    List<Trip> findByMeasurements_Vehicle(Vehicle car);

    @Query("SELECT t FROM Trip t WHERE t.trip_start BETWEEN :start AND :end")
    List<Trip> getTripsByDateRange(Date start, Date end);

    @Query("SELECT DISTINCT m.trip FROM Measurement m WHERE m.vehicle = :vehicleId")
    List<Trip> findAllByVehicleId(long vehicleId);

    @Query("SELECT m FROM Measurement m " +
            "WHERE m.trip.id = (SELECT MIN(t.id) FROM Trip t WHERE t.state = 0 OR t.state = 1) " +
            "AND m.timestamp = (SELECT MAX(m2.timestamp) FROM Measurement m2 WHERE m2.trip.id = (SELECT MIN(t.id) FROM Trip t WHERE t.state = 0 OR t.state = 1))")
    Optional<Measurement> findLatestMeasurementOfFirstUnfinishedTrip();

    @Query("SELECT t FROM Trip t WHERE t.state = 0 OR t.state = 1 ORDER BY t.id ASC")
    Trip findFirstUnfinishedTrip();

}
