package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "person")
public class Person extends IdentifiedEntity {

    @Column(length = 50, nullable = false)
    private String name;

    private Boolean driver;

    private Boolean owner;

    private Long currentTripID;

    @OneToMany
    private List<Vehicle> vehicles;

    @OneToMany
    private List<DrivingBehavior> drivingBehavior;

    @OneToMany
    @Column(name = "trip_id")
    private List<Trip> trip;

    private void startTrip() {
        // TODO: Implement
//        this.currentTripID = new Trip(Person person_id, vehicle_id ).getId();
    }

    private void endTrip() {
        // TODO: Implement
    }

}