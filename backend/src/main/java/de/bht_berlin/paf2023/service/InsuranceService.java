package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.entity.InsuranceCompany;
import de.bht_berlin.paf2023.repo.InsuranceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InsuranceService {
    private final InsuranceRepo insuranceRepo;
//    private final ContractRepo contractRepo;
//    private final InsuranceCompanyRepo insuranceCompanyRepo;

    @Autowired
    public InsuranceService(InsuranceRepo repository) {
        this.insuranceRepo = repository;
    }

    public List<Insurance> getAllInsurances() {
        return insuranceRepo.findAll();
    }

    public Insurance getInsuranceById(Long id) {
        return insuranceRepo.findById(id).orElse(null);
    }

    public Insurance getInsurancesByPerson(Long id) {
        return insuranceRepo.findById(id).orElse(null);
    }

    public Insurance addInsurance(Insurance insurance) {
        return insuranceRepo.save(insurance);
    }

    public Number getSpecifiedKiloMeters(Long id) {
//        return repository.findSpecifiedKiloMeters(id);
        return null;
    }

    public String deleteInsurance(Long id) {
        insuranceRepo.deleteById(id);
        return "Insurance removed! " + id;
    }

}
