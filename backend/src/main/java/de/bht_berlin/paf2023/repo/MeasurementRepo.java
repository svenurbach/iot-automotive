package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository for {@link Measurement} entities.
 */
@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {

    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId")
    List<Measurement> findByVehicle(long vehicleId);

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

    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId ORDER BY m.timestamp DESC")
    List<Measurement> findLastMeasurementByVehicleId(long vehicleId, Pageable pageable);

    @Query("SELECT m.trip FROM Measurement m WHERE m.vehicle.id = :vehicleId ORDER BY m.timestamp DESC LIMIT 1")
    Trip findLastTripByVehicleId(long vehicleId);

    @Query("SELECT m FROM Measurement m WHERE m.trip.id = :tripId ORDER BY m.timestamp DESC LIMIT 1")
    Measurement findLastMeasurementByTripId(long tripId);

    default Measurement findLastMeasurementByVehicleId(long vehicleId) {
        List<Measurement> measurements = findLastMeasurementByVehicleId(vehicleId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }

    /**
     * finds last measurement of a given vehicle prior to the current measurement. Checks looking for measurement
     * id lower than current measurement id
     *
     * @param vehicleId          vehicle id as long
     * @param currentMeasurement current measurement
     * @return last measurement
     */
    default Measurement findLastMeasurementBeforeCurrent(long vehicleId, Measurement currentMeasurement) {
        long currentId = currentMeasurement.getId();

        List<Measurement> measurements = findMeasurementsByVehicleIdAndIdLessThan(
                vehicleId, currentId, PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "timestamp"))
        );

        return measurements.isEmpty() ? null : measurements.get(measurements.size() - 1);
    }

    List<Measurement> findMeasurementsByVehicleIdAndIdLessThan(
            long vehicleId, long currentId, Pageable pageable
    );

    /**
     * finds last measurements with measurement type LocationMeasurement of a given vehicle id
     *
     * @param vehicleId vehicle id as long
     * @param pageable  amount of measurements to be returned
     * @return last location measurement
     */
    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId AND m.measurementType = 'LocationMeasurement' ORDER BY m.timestamp DESC")
    List<Measurement> findLastLocationMeasurementByVehicleId(long vehicleId, Pageable pageable);

    default Measurement findLastLocationMeasurementByVehicleId(long vehicleId) {
        List<Measurement> measurements = findLastLocationMeasurementByVehicleId(vehicleId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }

    /**
     * finds last measurements with measurement type LocationMeasurement of a given vehicle id prior to current
     * location measurement
     * measurement
     *
     * @param vehicleId vehicle id as long
     * @param timestamp timestamp of current measurement
     * @param pageable  amount of measurements to be returned
     * @return last location measurement
     */
    @Query("SELECT m FROM Measurement m WHERE m.vehicle.id = :vehicleId AND m.measurementType = 'LocationMeasurement' AND m.timestamp < :timestamp ORDER BY m.timestamp DESC")
    List<Measurement> findLastLocationBeforeNewMeasurement(long vehicleId, Date timestamp, Pageable pageable);

    default Measurement findLastLocationBeforeNewMeasurement(long vehicleId, Measurement measurement) {
        Date timestamp = measurement.getTimestamp();
        List<Measurement> measurements = findLastLocationBeforeNewMeasurement(vehicleId, timestamp, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }

    /**
     * finds last measurement with measurement type LocationMeasurement of a given trip
     *
     * @param tripId   trip id as long
     * @param pageable amount of measurements to be returned
     * @return last location measurement
     */
    @Query("SELECT m FROM Measurement m WHERE m.trip.id = :tripId AND m.measurementType = 'LocationMeasurement' ORDER" +
            " BY m.timestamp DESC")
    List<Measurement> findLastLocationMeasurementByTripId(long tripId, Pageable pageable);

    default Measurement findLastLocationMeasurementByTripId(long tripId) {
        List<Measurement> measurements = findLastLocationMeasurementByTripId(tripId, PageRequest.of(0, 1));
        return measurements.isEmpty() ? null : measurements.get(0);
    }


}
