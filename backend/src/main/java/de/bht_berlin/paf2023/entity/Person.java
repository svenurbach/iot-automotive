package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter
public class Person extends IdentifiedEntity {

    @Column(length = 50, nullable = false)
    private String name;

    private Date dateOfBirth;

    @OneToMany(mappedBy = "policyholder", cascade = CascadeType.ALL)
    private List<InsuranceContract> insuranceContracts;

}