package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Entity class representing location measurements.
 * This class inherits properties from the Measurement class.
 */
@Entity
@Getter
@Setter
public class LocationMeasurement extends Measurement {

    private Float latitude;
    private Float longitude;

    public LocationMeasurement() {
    }

    /**
     * Constructs a new LocationMeasurement object with the specified parameters.
     * @param timestamp The timestamp of the measurement.
     * @param location A list containing latitude and longitude values.
     * @param vehicle The vehicle associated with the measurement.
     */
    public LocationMeasurement(Date timestamp, List<Float> location, Vehicle vehicle) {
        this.latitude = location.get(0);
        this.longitude = location.get(1);
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());

    }
}