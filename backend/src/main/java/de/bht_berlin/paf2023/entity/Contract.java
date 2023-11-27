package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity @Getter @Setter
public class Contract extends IdentifiedEntity {

// - Int Vertragsnummer
// - String Name

    @Id @GeneratedValue
    private Long id;

    // TODO: Check if this has to be the primary key
    @Column(length = 10, nullable = false)
    String contractNumber;

    @Column(nullable = false)
    Date begin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    Insurance insurance;
}