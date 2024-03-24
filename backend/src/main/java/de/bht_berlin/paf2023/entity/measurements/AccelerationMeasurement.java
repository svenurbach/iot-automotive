package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing acceleration measurements.
 * This class inherits properties from the Measurement class.
 */
@Entity
@Getter
@Setter
public class AccelerationMeasurement extends Measurement {

    // The acceleration value of the measurement.
    private Integer acceleration;

    public AccelerationMeasurement() {
    }

    /**
     * Constructs a new AccelerationMeasurement object with the specified parameters.
     * @param timestamp The timestamp of the measurement.
     * @param acceleration The acceleration value.
     * @param vehicle The vehicle associated with the measurement.
     */
    public AccelerationMeasurement(Date timestamp, int acceleration, Vehicle vehicle) {
        this.acceleration = acceleration;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }
}