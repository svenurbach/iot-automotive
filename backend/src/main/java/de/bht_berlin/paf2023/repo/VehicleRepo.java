package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Vehicle} entities.
 */
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {



}
