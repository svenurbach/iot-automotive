package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class SteeringWheelMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float steeringWheelAngle;


    @ManyToOne
    private Trip trip;

    public SteeringWheelMeasurement() {
    }


    public SteeringWheelMeasurement(Date timestamp, Float steeringWheelAngle, Vehicle vehicle) {
        this.steeringWheelAngle = steeringWheelAngle;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());

    }
}