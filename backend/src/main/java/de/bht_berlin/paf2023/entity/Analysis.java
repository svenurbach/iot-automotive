package de.bht_berlin.paf2023.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Analysis extends IdentifiedEntity {

    private Boolean measurementError;

    private Boolean inconsistency;


    @OneToMany(mappedBy = "analysis")
    private List<DrivingBehavior> drivingBehavior;

    @OneToMany(mappedBy = "analysis")
    private List<Trip> trip;

    @OneToOne (mappedBy = "analysis")
    private Measurement measurement;
    @Id
    private Long id;

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
}
