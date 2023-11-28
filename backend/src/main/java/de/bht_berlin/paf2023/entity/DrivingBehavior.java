package de.bht_berlin.paf2023.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DrivingBehavior extends IdentifiedEntity{


    @Id
    private Long id;

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }

    private Integer riskWillingness;

    private Integer consumption;

    private Integer safety;

    private Integer evaluation;

}
