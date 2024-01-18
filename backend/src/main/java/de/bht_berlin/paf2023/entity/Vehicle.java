package de.bht_berlin.paf2023.entity;

import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Vehicle extends IdentifiedEntity {

    private Integer yearOfConstruction;

    private String licensePlate;

    private String vin;

    @ManyToOne
    private Contract contract;

    @ManyToOne
    private Person person;

    @ManyToOne
    private VehicleModel vehicleModel;

    @OneToMany
    private List<Measurement> measurement;

    // TODO: Brauchen wir hier EndLocationMeasurements oder Measurement?
//    @OneToMany
//    private EndLocationMeasurement endLocationMeasurement;

}