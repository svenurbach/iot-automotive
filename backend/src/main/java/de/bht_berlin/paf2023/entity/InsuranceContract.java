package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity @Getter @Setter
@Table(name = "contract")
public class InsuranceContract extends IdentifiedEntity {

    private String policyNumber;

    // Selbstbeteiligung
    private Long deductible;

    private Long contractDistance;

    private Float contractPrice;

    @Column(nullable = false)
    private Date begin;

    @ManyToOne
    @JsonIgnoreProperties("insuranceContracts")
    @JoinColumn(name = "person_id")
    private Person policyholder;

    @OneToOne
    @JsonIgnoreProperties("insuranceContract")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("insuranceContracts")
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

}