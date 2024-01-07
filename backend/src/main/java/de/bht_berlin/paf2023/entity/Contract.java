package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity @Getter @Setter
public class Contract extends IdentifiedEntity {

    String insuranceType;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    Date begin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person policyholderID;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicleID;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    Insurance insurance;
}