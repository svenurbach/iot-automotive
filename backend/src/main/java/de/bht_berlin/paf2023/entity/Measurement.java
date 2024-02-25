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

    @ManyToOne
    @JoinColumn(name = "vehicle")
    Vehicle vehicle;

    // getMeasuredValue()   -> Messwert wird von Sensoren über Request an die Anwendung geschickt?
    // setInterval()   ->wie oft werden Werte in DB geschrieben (alle ms, jede h ...)
    // saveInDB()   -> Messwert wird in DB geschrieben

    @ManyToOne
    private Analysis analysis;

    private long getMeasurementsFromOneTypeOfMeasurement(){
        // TO-DO:
        // holt sich alle aktuellen Messungen eines Types (Geschwindigkeit, Verbrauch, Beschleunigung, etc.)
    return 0;
    }
}