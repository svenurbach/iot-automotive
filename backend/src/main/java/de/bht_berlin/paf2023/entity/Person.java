package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "person")
public class Person extends IdentifiedEntity {

    @Column(length = 50, nullable = false)
    private String name;

    private Date dateOfBirth;

    private Long currentTripID;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "policyholder", cascade = CascadeType.ALL)
    private List<InsuranceContract> insuranceContracts;

    @OneToMany
    @Column(name = "trip_id")
    private List<Trip> trips;

    private void startTrip() {
        // TODO: Implement
//        this.currentTripID = new Trip(Person person_id, vehicle_id ).getId();
    }

    private void endTrip() {
        // TODO: Implement
    }

}