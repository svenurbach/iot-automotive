package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Getter @Setter
public class TirePressureMeasurement extends Measurement {

//    @Id @GeneratedValue
//    private Integer id;

    private Integer frontLeftTire;
    private Integer frontRightTire;
    private Integer backRightTire;
    private Integer backLeftTire;

    //todo test @yaman
    private List<Integer> parseList(){
        List<Integer> list = new ArrayList<>();
        list.add(backLeftTire);
        list.add(frontRightTire);
        list.add(backRightTire);
        list.add(backLeftTire);
        return list;
    }

}