package de.bht_berlin.paf2023.entity.measurements;

import de.bht_berlin.paf2023.entity.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class PedalMeasurement extends Measurement {

    @Id @GeneratedValue
    private Long id;

    private Float accelerationPedal;
    private Float brakePedal;
    private Float couplingPedal;
}