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
public class AccelerationMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Integer acceleration;

    public AccelerationMeasurement() {
    }


    public AccelerationMeasurement(Date timestamp, int acceleration, Vehicle vehicle) {
        this.acceleration = acceleration;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }
}