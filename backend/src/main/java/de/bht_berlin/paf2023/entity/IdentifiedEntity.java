package de.bht_berlin.paf2023.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@MappedSuperclass @Getter @Setter
public abstract class IdentifiedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}
