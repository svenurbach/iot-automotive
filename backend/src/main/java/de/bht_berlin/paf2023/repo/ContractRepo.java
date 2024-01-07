package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Contract} entities.
 */
public interface ContractRepo extends JpaRepository<Contract, Long> {



}
