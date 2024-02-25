package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link Insurance} entities.
 */
@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {

//    Number findSpecifiedKiloMeters(Long id);
    @Query("SELECT i FROM Insurance i JOIN i.contract c JOIN c.insuredPerson p WHERE p.id = :personId")
    List<Insurance> findInsurancesByPersonId(@Param("personId") Long personId);

}
