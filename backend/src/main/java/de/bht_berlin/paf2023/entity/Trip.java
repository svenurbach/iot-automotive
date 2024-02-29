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

    private Float start_latitude;
    private Float start_longitude;

    private Float end_latitude;
    private Float end_longitude;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

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