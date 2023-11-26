package de.bht_berlin.paf2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class InsuranceCompany extends IdentifiedEntity {

    @Id @GeneratedValue
    private Long id;

    String name;

    @OneToMany(mappedBy = "insurance")
    private List<Insurance> insuranceList;

}