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
public class LocationMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float latitude;
    private Float longitude;

    @ManyToOne
    private Trip trip;

    public LocationMeasurement() {
    }


    public LocationMeasurement(Date timestamp, List<Float> location, Vehicle vehicle) {
        this.latitude = location.get(0);
        this.longitude = location.get(1);
        this.setTimestamp(timestamp);
        this.setVehicle(vehicle);

    }
}