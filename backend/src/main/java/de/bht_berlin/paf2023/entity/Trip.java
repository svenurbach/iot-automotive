package de.bht_berlin.paf2023.entity;

import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "trip")
public class Trip extends IdentifiedEntity {

    private Date trip_start;
    private Date trip_end;

    private Long average_speed;

//    @OneToOne
//    private LocationMeasurement startLocation;
//
//    @OneToOne
//    private LocationMeasurement endLocation;

    private Float start_latitude;
    private Float start_longitude;

    private Float end_latitude;
    private Float end_longitude;

    @OneToMany
    @JoinColumn(name = "measurements")
    private List<Measurement> measurements;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Trip() {
        this.measurements = new ArrayList<>();
    }

    public Date getTrip_end() {
        return this.trip_end;
    }

    public Long get_average_speed() {
        return this.average_speed;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"id\": \"").append(getId()).append("\", ");
        builder.append("\"trip_start\": \"").append(trip_start).append("\", ");
        builder.append("\"trip_end\": \"").append(trip_end).append("\", ");
        builder.append("\"average_speed\": \"").append(average_speed).append("\", ");
//        builder.append("\"startLocation\": \"").append(startLocation).append("\", ");
//        builder.append("\"endLocation\": \"").append(endLocation).append("\", ");
        builder.append("\"vehicle\": \"").append(vehicle).append("\", ");
        builder.append("}");
        return builder.toString();
    }

    public void addMeasurement(Measurement measurement) {
        this.measurements.add(measurement);
    }
//    @Override
//    public String toString() {
//        return "{\"average_speed\": " + average_speed + ", \"trip_end\": \"" + trip_end + "\", \"trip_start\": \"" + trip_start + "\"}";
//    }

//    public startTrip(){
//        LocationMeasurement startLocation = new LocationMeasurement();
//    }

//    public Integer calcDuration(end,start){{
//    return end-start;
//    }

}