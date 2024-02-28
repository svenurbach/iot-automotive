package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
public class Insurance extends IdentifiedEntity {

    private String insuranceName;

    private String insuranceType;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InsuranceContract> contracts;

    @ManyToOne
    @JoinColumn(name = "insurance_company_id")
    private InsuranceCompany insuranceCompany;

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{");
//        builder.append("\"id\": \"").append(getId()).append("\", ");
//        builder.append("\"insuranceName\": \"").append(insuranceName).append("\", ");
//        builder.append("\"insuranceType\": \"").append(insuranceType).append("\", ");
//        builder.append("\"insuranceCompany\": \"").append(insuranceCompany).append("\", ");
//        builder.append("}");
//        return builder.toString();
//    }
}