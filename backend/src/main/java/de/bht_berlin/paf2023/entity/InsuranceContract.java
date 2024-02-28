package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity @Getter @Setter
//@ToString
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
//    @JsonIgnore
    @JoinColumn(name = "person_id")
    private Person policyholder;

    @OneToOne
//    @JsonIgnore
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{");
//        builder.append("\"id\": \"").append(getId()).append("\", ");
//        builder.append("\"dedpolicyNumbeructible\": \"").append(policyNumber).append("\", ");
//        builder.append("\"deductible\": \"").append(deductible).append("\", ");
//        builder.append("\"contractDistance\": \"").append(contractDistance).append("\", ");
//        builder.append("\"contractPrice\": \"").append(contractPrice).append("\", ");
//        builder.append("\"begin\": \"").append(begin).append("\", ");
//        builder.append("\"policyholder\": \"").append(policyholder).append("\", ");
//        builder.append("\"vehicle\": \"").append(vehicle).append("\", ");
//        builder.append("\"insurance\": \"").append(insurance).append("\"");
//        builder.append("}");
//        return builder.toString();
//    }

}