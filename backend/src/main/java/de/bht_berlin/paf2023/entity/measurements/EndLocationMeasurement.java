package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class EndLocationMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float endLatitude;
    private Float endlongitude;

    @ManyToOne
    private Trip trip;

    public EndLocationMeasurement() {
    }


    public EndLocationMeasurement(Date timestamp, List<Float> endLocation, Vehicle vehicle) {
        this.endLatitude = endLocation.get(0);
        this.endlongitude = endLocation.get(1);
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);

    }

}