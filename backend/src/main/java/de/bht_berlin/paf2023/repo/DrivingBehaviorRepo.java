package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.DrivingBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link DrivingBehavior} entities.
 */
@Repository
public interface DrivingBehaviorRepo extends JpaRepository<DrivingBehavior, Long> {



}
