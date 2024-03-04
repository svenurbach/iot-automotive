package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Measurement extends IdentifiedEntity {

    private Date timestamp;

    private Integer interval;

    private String measurementType;

    private Boolean isError;

    @ManyToOne()
    @JoinColumn(name = "vehicle")
    @JsonIgnore
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "trip")
    Trip trip;

    // getMeasuredValue()   -> Messwert wird von Sensoren über Request an die Anwendung geschickt?
    // setInterval()   ->wie oft werden Werte in DB geschrieben (alle ms, jede h ...)
    // saveInDB()   -> Messwert wird in DB geschrieben

    private long getMeasurementsFromOneTypeOfMeasurement() {
        // TO-DO:
        // holt sich alle aktuellen Messungen eines Types (Geschwindigkeit, Verbrauch, Beschleunigung, etc.)
        return 0;
    }
}