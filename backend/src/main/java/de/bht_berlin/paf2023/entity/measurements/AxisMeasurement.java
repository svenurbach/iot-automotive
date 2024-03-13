package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class AxisMeasurement extends Measurement {
    private Float axisAngle;

    public AxisMeasurement() {
    }


    public AxisMeasurement(Date timestamp, float axisAngle, Vehicle vehicle) {
        this.axisAngle = axisAngle;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());

    }
}