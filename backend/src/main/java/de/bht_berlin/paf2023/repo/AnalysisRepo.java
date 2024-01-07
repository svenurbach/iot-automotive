package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Analysis} entities.
 */
public interface AnalysisRepo extends JpaRepository<Analysis, Long> {



}
