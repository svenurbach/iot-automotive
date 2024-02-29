package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToOne
    @JsonIgnoreProperties("insuranceContract")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JsonIgnoreProperties("insuranceContracts")
    @JoinColumn(name = "person_id")
    private Person policyholder;

    @ManyToOne
    @JsonIgnoreProperties("insuranceContracts")
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    // Builder Pattern
    private InsuranceContract(Builder builder) {
        this.policyNumber = builder.policyNumber;
        this.deductible = builder.deductible;
        this.contractDistance = builder.contractDistance;
        this.contractPrice = builder.contractPrice;
        this.begin = builder.begin;
        this.vehicle = builder.vehicle;
        this.policyholder = builder.policyholder;
        this.insurance = builder.insurance;
    }

    public InsuranceContract() {

    }

//    @Setter
    public static class Builder {
        private String policyNumber;
        private Long deductible;
        private Long contractDistance;
        private Float contractPrice;
        private Date begin;
        private Vehicle vehicle;
        private Person policyholder;
        private Insurance insurance;

        public Builder setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
            return this;
        }

        public Builder setDeductible(Long deductible) {
            this.deductible = deductible;
            return this;
        }

        public Builder setContractDistance(Long contractDistance) {
            this.contractDistance = contractDistance;
            return this;
        }

        public Builder setContractPrice(Float contractPrice) {
            this.contractPrice = contractPrice;
            return this;
        }

        public Builder setBegin(Date begin) {
            this.begin = begin;
            return this;
        }

        public Builder setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setPolicyholder(Person policyholder) {
            this.policyholder = policyholder;
            return this;
        }

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public InsuranceContract build() {
            return new InsuranceContract(this);
        }
    }

}