package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Measurement extends IdentifiedEntity {

//    @Id @GeneratedValue
//    private Integer id;

    private Date timestamp;

    private Integer interval;

    private Long measuredValue;

    private String measurementType;

    private Boolean isError;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "trip")
    Trip trip;

    // getMeasuredValue()   -> Messwert wird von Sensoren über Request an die Anwendung geschickt?
    public Long getMeasuredValue(){
        return this.measuredValue;
    }


    // saveInDB()   -> Messwert wird in DB geschrieben

    private long getMeasurementsFromOneTypeOfMeasurement() {
        // TO-DO:
        // holt sich alle aktuellen Messungen eines Types (Geschwindigkeit, Verbrauch, Beschleunigung, etc.)
        return 0;
    }
}