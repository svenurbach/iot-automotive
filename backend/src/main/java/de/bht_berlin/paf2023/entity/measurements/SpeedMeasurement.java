package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing speed measurements.
 * This class inherits properties from the Measurement class.
 */
@Entity
@Getter
@Setter
public class SpeedMeasurement extends Measurement {

    private Integer speed;

    public SpeedMeasurement() {
    }

    /**
     * Constructs a new SpeedMeasurement object with the specified parameters.
     * @param timestamp The timestamp of the measurement.
     * @param speed The speed value.
     * @param vehicle The vehicle associated with the measurement.
     */
    public SpeedMeasurement(Date timestamp, int speed, Vehicle vehicle) {
        this.speed = speed;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }
}