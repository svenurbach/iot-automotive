package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for {@link Measurement} entities.
 */
@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {

    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId")
    List<Measurement> findByVehicle(long vehicleId);

//    List<Measurement> findMeasurements_Vehicle(Vehicle car);

    @Query("SELECT m FROM Measurement m WHERE m.measurementType = :measurementType")
    List<Measurement> findMeasurementType(String measurementType);

    @Query("SELECT m FROM Measurement m WHERE m.measurementType = :measurementType and m.trip.id = :trip")
    List<Measurement> findMeasurementTypeInTrip(String measurementType, long trip);

    @Query("SELECT m FROM Measurement m WHERE m.isError = :measurementError")
    List<Measurement> findMeasurementError(boolean measurementError);

    @Query("SELECT m FROM Measurement m WHERE m.isError = true AND m.vehicle.id = :vehicleId")
    List<Measurement> findAllMeasurementsFromVehicleWithError(long vehicleId);

    @Query("SELECT m FROM Measurement m WHERE m.trip.id = :tripId")
    List<Measurement> getAllMeasurementsFromTrip(long tripId);


//    acceleration, axis_angle, back_left_tire, back_right_tire, front_left_tire, front_right_tire, fuel_level, speed, steering_wheel_angle

    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId ORDER BY m.timestamp DESC")
    List<Measurement> findLastMeasurementByVehicleId(long vehicleId, Pageable pageable);

    default Measurement findLastMeasurementByVehicleId(long vehicleId) {
        List<Measurement> measurements = findLastMeasurementByVehicleId(vehicleId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }


    default Measurement findLastMeasurementBeforeCurrent(long vehicleId, Measurement currentMeasurement) {
        long currentId = currentMeasurement.getId();

        // Retrieve measurements for the specified vehicle with IDs less than the current measurement's ID
        List<Measurement> measurements = findMeasurementsByVehicleIdAndIdLessThan(
                vehicleId, currentId, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "timestamp"))
        );

        // Return the last measurement from the filtered list
        return measurements.isEmpty() ? null : measurements.get(measurements.size() - 1);
    }

    List<Measurement> findMeasurementsByVehicleIdAndIdLessThan(
            long vehicleId, long currentId, Pageable pageable
    );


    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId AND m.measurementType = 'LocationMeasurement' ORDER BY m.timestamp DESC")
    List<Measurement> findLastLocationMeasurementByVehicleId(long vehicleId, Pageable pageable);

    default Measurement findLastLocationMeasurementByVehicleId(long vehicleId) {
        List<Measurement> measurements = findLastLocationMeasurementByVehicleId(vehicleId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }

    @Query("SELECT m FROM Measurement m WHERE m.trip.id = :tripId AND m.measurementType = 'LocationMeasurement' ORDER" +
            " BY m.timestamp DESC")
    List<Measurement> findLastLocationMeasurementByTripId(long tripId, Pageable pageable);

    default Measurement findLastLocationMeasurementByTripId(long tripId) {
        List<Measurement> measurements = findLastLocationMeasurementByTripId(tripId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }

    @Query("SELECT m.trip FROM Measurement m WHERE m.vehicle.id = :vehicleId ORDER BY m.timestamp DESC LIMIT 1")
    Trip findLastTripByVehicleId(long vehicleId);

    @Query("SELECT m FROM Measurement m WHERE m.trip.id = :tripId ORDER BY m.timestamp DESC LIMIT 1")
    Measurement findLastMeasurementByTripId(long tripId);
}
