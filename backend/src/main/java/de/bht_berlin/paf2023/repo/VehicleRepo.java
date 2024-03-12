package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.InsuranceContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for {@link Vehicle} entities.
 */
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v " +
            "FROM Vehicle v " +
            "JOIN v.insuranceContract c " +
            "JOIN c.policyholder p " +
            "WHERE p.id = :personId")
    List<Vehicle> findByInsuranceContract_Policyholder(@Param("personId") Long personId);

}
