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
public class EndLocationMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float endLatitude;
    private Float endlongitude;

    @ManyToOne
    private Trip trip;

    public EndLocationMeasurement() {
    }


    public EndLocationMeasurement(Date timestamp, List<Float> startLocation) {
        this.endLatitude = startLocation.get(0);
        this.endlongitude = startLocation.get(1);
        this.setTimestamp(timestamp);

    }

}