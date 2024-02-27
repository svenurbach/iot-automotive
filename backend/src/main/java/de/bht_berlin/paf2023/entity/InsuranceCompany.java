package de.bht_berlin.paf2023.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class InsuranceCompany extends IdentifiedEntity {

    private String companyName;

    @OneToMany
    private List<Insurance> insurances;
}