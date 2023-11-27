package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity @Getter @Setter
public class Trip extends IdentifiedEntity {

// - Datetime Anfangszeit
// - Datetime Endzeit
// - Position Start
// - Position Ziel
// - Fahrzeug Fahrzeug

    @Id @GeneratedValue
    private Long id;

    Date start;
    Date end;

    Measurement.Position startLocation;
    Measurement.Position endLocation;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    // Driver
    @ManyToMany(mappedBy = "trip")
    Person person;

}