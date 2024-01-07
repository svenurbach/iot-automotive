package de.bht_berlin.paf2023.entity;

import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Person extends IdentifiedEntity {

    @Id @GeneratedValue
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    private Boolean driver;

    private Boolean owner;

    private Long currentTripID;

    @OneToMany
    private List <DrivingBehavior> drivingBehavior;

    @OneToMany
    private List<Trip> trip;

    private void startTrip() {
        // TODO: Implement
//        this.currentTripID = new Trip(Person person_id, vehicle_id ).getId();
    }

    private void endTrip() {
        // TODO: Implement
    }

}