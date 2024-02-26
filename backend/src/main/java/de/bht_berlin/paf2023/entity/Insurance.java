package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    private String insuranceName;

    private String insuranceType;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<InsuranceContract> contracts;

    @ManyToOne
    @JoinColumn(name = "insurance_company_id")
    private InsuranceCompany insuranceCompany;
}