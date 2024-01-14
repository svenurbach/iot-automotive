package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Vehicle extends IdentifiedEntity {

//    @Id @GeneratedValue
//    private Integer id;

    private Integer yearOfConstruction;

    private String licensePlate;

    @OneToOne
    private Contract contract;

    private String vin;

    @ManyToOne
    private Person person;

    @ManyToOne
    private VehicleModel vehicleModel;

    @OneToMany
    private List<Measurement> measurement;

}