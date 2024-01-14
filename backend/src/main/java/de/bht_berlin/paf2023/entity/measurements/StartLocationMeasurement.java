package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class StartLocationMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float startLatitude;
    private Float startLongitude;

    @ManyToOne
    private Trip trip;

    public StartLocationMeasurement() {
    }


    public StartLocationMeasurement(Date timestamp, List<Float> startLocation) {
        this.startLatitude = startLocation.get(0);
        this.startLongitude = startLocation.get(1);
        this.setTimestamp(timestamp);

    }
}