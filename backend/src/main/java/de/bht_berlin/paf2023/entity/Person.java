package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Person extends IdentifiedEntity {

    // - String Name
    // - Boolean Autofahrer
    // - Boolean Autohalter
    // - Fahrverhalten Fahrverhalten

    @Id @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    String name;

    Boolean driver;

    Boolean owner;

    DrivingBehaviour drivingBehaviour;

    // https://www.baeldung.com/jpa-many-to-many
    @ManyToMany
    @JoinTable(
            name = "trips",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    Trip trip;

    private void startTrip() {
        // TODO: Implement
    }

    private void endTrip() {
        // TODO: Implement
    }

}