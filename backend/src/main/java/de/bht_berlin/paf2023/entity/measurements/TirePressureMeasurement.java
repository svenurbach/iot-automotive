package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class TirePressureMeasurement extends Measurement {

    @Id @GeneratedValue
    private Long id;

    private Integer frontLeftTire;
    private Integer frontRightTire;
    private Integer backRightTire;
    private Integer backLeftTire;
}