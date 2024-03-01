package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link Trip} entities.
 */
@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

    List<Trip> findByMeasurements_Vehicle(Vehicle car);

}
