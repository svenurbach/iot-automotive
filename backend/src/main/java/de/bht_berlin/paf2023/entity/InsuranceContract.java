package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity @Getter @Setter
@Table(name = "contract")
public class InsuranceContract extends IdentifiedEntity {

    private String policyNumber;

    // Selbstbeteiligung
    private Long deductible;

    private Long contractDistance;

    private Float contractPrice;

    @Column(nullable = false)
    private Date begin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person policyholder;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

}