package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing generic measurements.
 * This class inherits properties from the IdentifiedEntity class.
 */
@Entity
@Getter
@Setter
public class Measurement extends IdentifiedEntity {

    private Date timestamp;

    private Long measuredValue;

    private String measurementType;

    private Boolean isError;

    @ManyToOne()
    @JoinColumn(name = "vehicle")
    @JsonIgnore
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "trip")
    Trip trip;

}