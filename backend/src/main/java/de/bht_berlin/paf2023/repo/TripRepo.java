package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT t FROM Trip t WHERE t.state = 0 OR t.state = 1 ORDER BY t.id ASC LIMIT 1")
    Trip findFirstUnfinishedTrip();

    @Query("SELECT m.trip FROM Measurement m WHERE m.vehicle.id IN :vehicleIds")
    List<Trip> findAllByVehicleIds(@Param("vehicleIds") List<Long> vehicleIds);

    /**
     * finds all trips of multiple vehicle ids in a given date range
     *
     * @param vehicleIds vehicle ids as long
     * @param startTime  start date as string formatte as: 2018-01-17T05:01:33.000Z
     * @param endTime    end date as string formatte as: 2018-01-17T05:01:33.000Z
     * @return list of trips
     */
    @Query("SELECT DISTINCT m.trip FROM Measurement m WHERE m.vehicle.id IN :vehicleIds AND m.trip.trip_start BETWEEN :startTime AND :endTime")
    List<Trip> findAllByVehicleIdsAndDateRange(@Param("vehicleIds") List<Long> vehicleIds, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query("SELECT m.vehicle FROM Measurement m WHERE m.trip.id = :tripId")
    Optional<Vehicle> findVehicleByTripId(@Param("tripId") Long tripId);


}
