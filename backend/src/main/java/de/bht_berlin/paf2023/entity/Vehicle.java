package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Vehicle extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    String title;

    @Lob
    String description;

    @Lob
    @JsonIgnore
    byte[] image;

    @Column(nullable = false)
    Integer price;

    Boolean instock;

}