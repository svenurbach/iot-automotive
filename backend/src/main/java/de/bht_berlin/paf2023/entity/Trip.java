package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private Float start_latitude;
    private Float start_longitude;

    private Float end_latitude;
    private Float end_longitude;

    public enum TripState {
        RUNNING,
        PAUSED,
        FINISHED
    }

    private TripState state;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("trip")
    private List<Measurement> measurements;

    public Trip() {
        this.state = TripState.RUNNING;
    }

    public Trip(LocationMeasurement startLocation) {
        this.state = TripState.RUNNING;

    }

    public void start(LocationMeasurement startLocation) {
        if (state == TripState.RUNNING) {
            System.out.println("Trip is already started.");
        } else {
            state = TripState.RUNNING;
            System.out.println("Trip started.");
        }
    }

    public void pause() {
        if (state == TripState.RUNNING) {
            state = TripState.PAUSED;
            System.out.println("Trip paused.");
        } else {
            System.out.println("Cannot pause trip. Trip is not running.");
        }
    }

    public void finish(LocationMeasurement endLocation) {
        if (state == TripState.FINISHED || state == TripState.PAUSED) {
            System.out.println("Trip already finished.");
        } else {
            state = TripState.FINISHED;
            System.out.println("Finish trip.");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"id\": \"").append(getId()).append("\", ");
        builder.append("\"trip_start\": \"").append(trip_start).append("\", ");
        builder.append("\"trip_end\": \"").append(trip_end).append("\", ");
//        builder.append("\"average_speed\": \"").append(average_speed).append("\", ");
//        builder.append("\"startLocation\": \"").append(startLocation).append("\", ");
//        builder.append("\"endLocation\": \"").append(endLocation).append("\", ");
//        builder.append("\"vehicle\": \"").append(vehicle).append("\", ");
        builder.append("}");
        return builder.toString();
    }

}