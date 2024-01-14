package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

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

}