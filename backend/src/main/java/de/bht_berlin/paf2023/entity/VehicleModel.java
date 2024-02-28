package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class VehicleModel extends IdentifiedEntity {

    private String name;

    private String manufacturer;

    private Float weight;

    private String tireType;

    private Boolean airbag;

    private String engine;

    private Boolean seatHeater;

    private Boolean gps;

    private String fueltype;

    @OneToMany(mappedBy = "vehicleModel", cascade = CascadeType.ALL)
    private List<Vehicle> vehicle;

}