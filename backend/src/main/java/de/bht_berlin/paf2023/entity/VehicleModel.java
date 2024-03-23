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
public class VehicleModel extends IdentifiedEntity {

    private String modelName;

    private String manufacturer;

    private Float weight;

    private Float maxSpeed;

    private Float maxAcceleration;

    private Float maxAxis;
    private Float minAxis;

    private Float maxSteeringWheel;

    private Float minSteeringWheel;

    private String imgURL;

    private Float speedTolerance;

    private Float accelerationTolerance;

    private Float locationTolerance;

    private Float AxisTolerance;

    private Float SteeringWheelTolerance;

    @OneToMany(mappedBy = "vehicleModel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vehicle> vehicles;

}