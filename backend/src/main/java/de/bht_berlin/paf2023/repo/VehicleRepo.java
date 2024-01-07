package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Vehicle} entities.
 */
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {



}
