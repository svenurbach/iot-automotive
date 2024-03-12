package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Vehicle extends IdentifiedEntity {

    @Column(length = 4)
    private Integer yearOfConstruction;

    private String licensePlate;

    // Vehicle Identification Number
    private String vin;

    @OneToOne(mappedBy = "vehicle")
    private InsuranceContract insuranceContract;

    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    private VehicleModel vehicleModel;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Measurement> measurements;

}