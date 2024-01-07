package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter
public class Trip extends IdentifiedEntity {

    @Id @GeneratedValue
    private Integer id;

    private Date start;
    private Date end;

    @OneToMany
    private List<LocationMeasurement> startLocation;

    @OneToMany
    private List<LocationMeasurement> endLocation;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Person person;

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