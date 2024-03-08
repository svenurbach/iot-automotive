package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Measurement extends IdentifiedEntity {

//    @Id @GeneratedValue
//    private Integer id;

    private Date timestamp;

    private Long measuredValue;

    private String measurementType;

    private Boolean isError;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "trip")
    Trip trip;

    public Long getMeasuredValue(){
        return this.measuredValue;
    }


}