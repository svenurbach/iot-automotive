package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Entity
@Getter
@Setter
public class SpeedMeasurement extends Measurement {

    @Autowired
    private MeasurementRepo measurementRepo;

//    @Id @GeneratedValue
//    private Integer id;

    private Integer speed;

    public SpeedMeasurement() {
    }


    public SpeedMeasurement(Date timestamp, int speed, Vehicle vehicle) {
        this.speed = speed;
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);
        this.setMeasurementType(this.getClass().getSimpleName());
    }




}