package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Person} entities.
 */
public interface PersonRepo extends JpaRepository<Person, Long> {



}
