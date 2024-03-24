package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link InsuranceCompany} entities.
 */
@Repository
public interface InsuranceCompanyRepo extends JpaRepository<InsuranceCompany, Long> {


}
