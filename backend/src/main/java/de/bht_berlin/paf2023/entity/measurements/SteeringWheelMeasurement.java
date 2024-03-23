package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing steering wheel measurements.
 * This class inherits properties from the Measurement class.
 */
@Entity
@Getter
@Setter
public class SteeringWheelMeasurement extends Measurement {

    private Float steeringWheelAngle;

    public SteeringWheelMeasurement() {
    }

    /**
     * Constructs a new SteeringWheelMeasurement object with the specified parameters.
     * @param timestamp The timestamp of the measurement.
     * @param steeringWheelAngle The angle of the steering wheel measurement.
     * @param vehicle The vehicle associated with the measurement.
     */
    public SteeringWheelMeasurement(Date timestamp, Float steeringWheelAngle, Vehicle vehicle) {
        this.steeringWheelAngle = steeringWheelAngle;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }
}