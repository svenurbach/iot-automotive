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

    /**
     * Retrieves a list of vehicles associated with a policyholder.
     * @param personId The ID of the policyholder.
     * @return A list of vehicles associated with the specified policyholder.
     */
    @Query("SELECT v " +
            "FROM Vehicle v " +
            "JOIN v.insuranceContract c " +
            "JOIN c.policyholder p " +
            "WHERE p.id = :personId")
    List<Vehicle> findByInsuranceContract_Policyholder(@Param("personId") Long personId);

    /**
     * Retrieves the vehicle associated with a specific insurance contract.
     * @param insuranceId The ID of the insurance contract.
     * @return The vehicle associated with the specified insurance contract.
     */
    @Query("SELECT v " +
            "FROM Vehicle v " +
            "JOIN v.insuranceContract c " +
            "JOIN c.policyholder p " +
            "WHERE c.id = :insuranceId")
    Vehicle findByInsuranceContract(@Param("insuranceId") Long insuranceId);

}
