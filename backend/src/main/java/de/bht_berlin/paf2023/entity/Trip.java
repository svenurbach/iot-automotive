package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.DateAndTime;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{\"trip_start\": ");
//        builder.append(trip_start);
//        builder.append(", \"trip_end\": ");
//        builder.append(trip_end);
//        builder.append(", \"average_speed\":");
//        builder.append(average_speed);
//        builder.append("}");
//        return builder.toString();
//    }

    @Override
    public String toString() {
        return "{\"average_speed\": " + average_speed + ", \"trip_end\": \"" + trip_end + "\", \"trip_start\": \"" + trip_start + "\"}";
    }
////        return "Trip{" +
////                "trip_start='" + trip_start + '\'' +
////                ", trip_end='" + trip_end + '\'' +
////                ", average_speed='" + average_speed + '\'' +

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