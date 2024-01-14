package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class FuelMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Integer fuelLevel;

    public FuelMeasurement() {
    }


    public FuelMeasurement(Date timestamp, int fuelLevel) {
        this.fuelLevel = fuelLevel;
        this.setTimestamp(timestamp);

    }
}