package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "trip")
public class Trip extends IdentifiedEntity {

//    @Id @GeneratedValue
//    private Integer id;

    private Date start;
    private Date end;

    @OneToMany
    @JoinColumn(name = "startLocation")
    private List<LocationMeasurement> startLocation;

    @OneToMany
    @JoinColumn(name = "endLocation")
    private List<LocationMeasurement> endLocation;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "analysis")
    private Analysis analysis;

//    public Trip(vehicle...){
//        this.vehicle = vehicle;
//    }

//    public startTrip(){
//        LocationMeasurement startLocation = new LocationMeasurement();
//    }

//    public Integer calcDuration(end,start){{
//    return end-start;
//    }

}