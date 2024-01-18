package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity @Getter @Setter
public class Contract extends IdentifiedEntity {

    String insuranceNumber;

    @Column(nullable = false)
    Date begin;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person policyholderID;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicleID;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    Insurance insurance;

    Long contract_distance;

    public Long get_contract_distance(){
        return this.contract_distance;
    }
}