package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class TirePressureMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Integer frontLeftTire;
    private Integer frontRightTire;
    private Integer backRightTire;
    private Integer backLeftTire;

    public TirePressureMeasurement() {
    }


    public TirePressureMeasurement(Date timestamp, List<Integer> tirePressure) {
        this.frontLeftTire = tirePressure.get(0);
        this.frontRightTire = tirePressure.get(1);
        this.backRightTire = tirePressure.get(2);
        this.backLeftTire = tirePressure.get(3);
        this.setTimestamp(timestamp);

    }

    //todo test @yaman
    private List<Integer> parseList() {
        List<Integer> list = new ArrayList<>();
        list.add(backLeftTire);
        list.add(frontRightTire);
        list.add(backRightTire);
        list.add(backLeftTire);
        return list;
    }

}