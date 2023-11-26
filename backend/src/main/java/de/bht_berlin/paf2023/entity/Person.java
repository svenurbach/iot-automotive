package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Person extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;

    String name;

}