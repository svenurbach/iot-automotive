package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Repository for {@link Insurance} entities.
 */
@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {

//    @Query("SELECT i FROM Insurance i JOIN i.contracts c JOIN i.insurance_company ic WHERE person_id = :personId")
//    List<Insurance> findInsurancesByPersonId(@Param("personId") Long personId);

}
