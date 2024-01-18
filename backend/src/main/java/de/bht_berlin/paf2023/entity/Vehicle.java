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

    @JoinColumn(name = "contract_id")
    @ManyToOne
    private Contract contractID;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private Person personID;

    @JoinColumn(name = "vehicle_model_id")
    @ManyToOne
    private VehicleModel vehicleModelID;

    @OneToMany
    private List<Measurement> measurement;

}