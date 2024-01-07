package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.DateAndTime;
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

    private Date trip_start;
    private Date trip_end;

    private Long average_speed;

    @OneToMany
    @JoinColumn(name = "startLocation")
    private List<LocationMeasurement> startLocation;

    @OneToMany
    @JoinColumn(name = "endLocation")
    private List<LocationMeasurement> endLocation;


    @ManyToOne
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "analysis")
    private Analysis analysis;

    public Date getTrip_start() {
        return this.trip_start;
    }

    public Date getTrip_end() {
        return this.trip_end;
    }

    public Long get_average_speed(){
        return this.average_speed;

    }
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