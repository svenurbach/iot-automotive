package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for {@link VehicleModel} entities.
 */
@Repository
public interface VehicleModelRepo extends JpaRepository<VehicleModel, Long> {

    }


