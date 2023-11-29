package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    private List<Contract> contract;
}