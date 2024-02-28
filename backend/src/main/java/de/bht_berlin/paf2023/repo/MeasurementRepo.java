package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link Measurement} entities.
 */
@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {


    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId")
    List<Measurement> findByVehicle(long vehicleId);

    @Query("SELECT m FROM Measurement m WHERE m.measurementType = :measurementType")
    List<Measurement> findByMeasurementType(String measurementType);

}
