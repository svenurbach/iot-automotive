package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Vehicle extends IdentifiedEntity {

    @Column(length = 4)
    private Integer yearOfConstruction;

    private String licensePlate;

    // Vehicle Identification Number
    private String vin;

    @OneToOne(mappedBy = "vehicle")
    private InsuranceContract insuranceContract;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Trip> trips;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModel vehicleModel;

    @OneToMany
    @JsonIgnore
    private List<Measurement> measurement;

}