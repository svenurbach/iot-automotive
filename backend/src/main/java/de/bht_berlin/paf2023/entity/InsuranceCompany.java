package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
public class InsuranceCompany extends IdentifiedEntity {

    private String companyName;

    @OneToMany(mappedBy = "insuranceCompany", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Insurance> insurances;

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{");
//        builder.append("\"id\": \"").append(getId()).append("\", ");
//        builder.append("\"companyName\": \"").append(companyName).append("\", ");
//        builder.append("}");
//        return builder.toString();
//    }
}