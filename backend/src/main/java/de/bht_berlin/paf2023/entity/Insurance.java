package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    private String policyholder;

    private String insuranceType;

    @OneToOne
    private Vehicle vehicle;

    @OneToMany
    private List<Contract> contracts;

    @ManyToOne
    private InsuranceCompany insuranceCompany;
}