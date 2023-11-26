package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class VehicleModel extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String manufacturer;

    private Float weight;

    private String tireType;

//    private String[] airbag;

    private String engine;

//    private String[] seatHeater;
//
    private Boolean GPS;

    private String fueltype;

    @OneToMany(mappedBy = "vehicle")
    private List<Vehicle> vehicle;

}