package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity @Getter @Setter
public class Contract extends IdentifiedEntity {

    private String policyNumber;

    // Selbstbeteiligung
    private Number deductible;

    private Long contractDistance;

    private Float contractPrice;

    @Column(nullable = false)
    private Date begin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person policyholderID;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicleID;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

}