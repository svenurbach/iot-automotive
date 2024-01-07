package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass @Getter @Setter
public abstract class IdentifiedEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
