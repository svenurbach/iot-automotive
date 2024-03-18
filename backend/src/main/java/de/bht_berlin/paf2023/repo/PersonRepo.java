package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Person} entities.
 */
@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {


}
