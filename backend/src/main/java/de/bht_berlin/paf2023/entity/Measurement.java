package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    public Long getMeasuredValue(){
        return this.measuredValue;
    }


}