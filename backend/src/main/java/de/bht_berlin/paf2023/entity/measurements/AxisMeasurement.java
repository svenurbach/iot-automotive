package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing axis measurements.
 * This class inherits properties from the Measurement class.
 */
@Entity
@Getter
@Setter
public class AxisMeasurement extends Measurement {

    // The angle of the axis measurement.
    private Float axisAngle;

    public AxisMeasurement() {
    }

    /**
     * Constructs a new AxisMeasurement object with the specified parameters.
     * @param timestamp The timestamp of the measurement.
     * @param axisAngle The angle of the axis measurement.
     * @param vehicle The vehicle associated with the measurement.
     */
    public AxisMeasurement(Date timestamp, float axisAngle, Vehicle vehicle) {
        this.axisAngle = axisAngle;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }
}