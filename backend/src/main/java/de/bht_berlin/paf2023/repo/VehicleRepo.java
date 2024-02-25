package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link Vehicle} entities.
 */
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    // getLastPositionByCar()
//    @Query("SELECT v FROM Vehicle v JOIN i.contract c JOIN c.insuredPerson p WHERE p.id = :carId")
    EndLocationMeasurement findLastPositionByCarId(@Param("carId") Long carId);

    // getLastPositionsByPerson()

//    @Query("SELECT i FROM Vehicle i JOIN i.contract c JOIN c.insuredPerson p WHERE p.id = :personId")
    List<EndLocationMeasurement> findLastPositionByPersonId(@Param("personId") Long personId);

}
