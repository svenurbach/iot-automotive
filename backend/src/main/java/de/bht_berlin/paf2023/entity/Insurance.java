package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;
    String name;
    String insuranceType;

    @ManyToOne
    @JoinColumn(name = "insurance_company_id")
    InsuranceCompany insuranceCompanyID;

    @ManyToOne
    @JoinColumn(name = "policyholder_id")
    Person policyholderID;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicleID;
}