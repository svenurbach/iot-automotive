package de.bht_berlin.paf2023.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter
public class Measurement extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;

    private Date timestamp;

    private Integer interval;

    // getMeasuredValue()   -> Messwert wird von Sensoren über Request an die Anwendung geschickt?
    // setInterval()   ->wie oft werden Werte in DB geschrieben (alle ms, jede h ...)
    // saveInDB()   -> Messwert wird in DB geschrieben
}