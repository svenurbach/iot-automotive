package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link VehicleModel} entities.
 */
@Repository
public interface VehicleModelRepo extends JpaRepository<VehicleModel, Long> {

    @Query("select vm from VehicleModel vm where vm.maxSpeed = :maxSpeed")
    List<VehicleModel> getMaxSpeed(long maxSpeed);

    @Query("select vm from VehicleModel vm where vm.maxAcceleration = :maxAcceleration")
    List<VehicleModel> getMaxAcceleration(long maxAcceleration);


    }


