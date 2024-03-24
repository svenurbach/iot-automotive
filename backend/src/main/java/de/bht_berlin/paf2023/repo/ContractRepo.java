package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.InsuranceContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link InsuranceContract} entities.
 */
@Repository
public interface ContractRepo extends JpaRepository<InsuranceContract, Long> {

}
