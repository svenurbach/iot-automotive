package de.bht_berlin.paf2023.entity;

import de.bht_berlin.paf2023.entity.measurements.StartLocationMeasurement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity @Getter @Setter
@Table(name = "trip")
public class Trip extends IdentifiedEntity {

    private Date trip_start;
    private Date trip_end;

    private Long average_speed;

    @OneToMany
    @JoinColumn(name = "startLocation")
    private List<StartLocationMeasurement> startLocation;

    @OneToMany
    @JoinColumn(name = "endLocation")
    private List<StartLocationMeasurement> endLocation;

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
        builder.append("\"startLocation\": \"").append(startLocation).append("\", ");
        builder.append("\"endLocation\": \"").append(endLocation).append("\", ");
        builder.append("\"vehicle\": \"").append(vehicle).append("\", ");
        builder.append("\"person\": \"").append(person).append("\", ");
        builder.append("\"analysis\": ").append(analysis);
        builder.append("}");
        return builder.toString();
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