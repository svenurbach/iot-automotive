package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Insurance} entities.
 */
@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {

    // Custom Methods/SQL-Queries


}
