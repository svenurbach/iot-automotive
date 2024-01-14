package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class AxisMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Float axisAngle;

    public AxisMeasurement() {
    }


    public AxisMeasurement(Date timestamp, float axisAngle) {
        this.axisAngle = axisAngle;
        this.setTimestamp(timestamp);

    }
}