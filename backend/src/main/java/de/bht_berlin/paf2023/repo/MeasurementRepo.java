package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Measurement} entities.
 */
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {



}
