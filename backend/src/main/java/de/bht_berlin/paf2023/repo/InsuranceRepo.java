package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Insurance} entities.
 */
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {

    // Custom Methods/SQL-Queries


}
