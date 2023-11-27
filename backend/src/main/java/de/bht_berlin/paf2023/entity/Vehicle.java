package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Vehicle extends IdentifiedEntity {

// - Fahrzeugmodell Fahrzeugmodell
// - Position Position
// - Int Baujahr
// - Person Person
// X- Zustand Zustand
// - String KFZ-Kennzeichen
// - Versicherung Versicherung
// - String Fahrzeuggestellnummer

    @Id @GeneratedValue
    private Long id;

    VehicleModel vehicleModel;

    Position position;

    Integer yearOfConstruction;

    String licensePlate;

    Insurance insurance;

    String vin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    VehicleModel vehicleModel;

}