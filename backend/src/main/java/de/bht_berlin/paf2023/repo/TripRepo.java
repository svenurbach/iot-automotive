package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link Trip} entities.
 */
public interface TripRepo extends JpaRepository<Trip, Long> {

    List<Trip> findAllByVehicle(Long vehicleId);

}
