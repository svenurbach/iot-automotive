package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
@Table(name = "vehicle")
public class Vehicle extends IdentifiedEntity {

    @Column(length = 4)
    private Integer yearOfConstruction;

    private String licensePlate;

    private String vin;

    @JoinColumn(name = "contract_id")
    @OneToOne
    private InsuranceContract insuranceContractID;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person personID;

    @JoinColumn(name = "vehicle_model_id")
    @ManyToOne
    private VehicleModel vehicleModelID;

    @OneToMany
    private List<Measurement> measurement;

}