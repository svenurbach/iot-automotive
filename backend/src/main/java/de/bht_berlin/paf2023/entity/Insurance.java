package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    private String insuranceName;

    private String insuranceType;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<InsuranceContract> insuranceContracts;

    @ManyToOne
    @JoinColumn(name = "insurance_company_id")
    @JsonIgnoreProperties("insurances")
    private InsuranceCompany insuranceCompany;

}