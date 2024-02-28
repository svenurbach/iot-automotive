package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.entity.InsuranceContract;
import de.bht_berlin.paf2023.repo.ContractRepo;
import de.bht_berlin.paf2023.repo.InsuranceRepo;
import de.bht_berlin.paf2023.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InsuranceService {
    private final InsuranceRepo insuranceRepo;
    private final ContractRepo contractRepo;
    private final PersonRepo personRepo;

    @Autowired
    public InsuranceService(InsuranceRepo repository, ContractRepo contractRepo, PersonRepo personRepo) {
        this.insuranceRepo = repository;
        this.contractRepo = contractRepo;
        this.personRepo = personRepo;
    }

    public List<InsuranceContract> getAllContracts() {
        return contractRepo.findAll();
    }

    public Insurance getInsuranceById(Long id) {
        return insuranceRepo.findById(id).orElse(null);
    }

    public Insurance getInsuranceByCar(Long id) {
        return insuranceRepo.findById(id).orElse(null);
    }

    public List<InsuranceContract> getInsurancesByPerson(Long id) {
        return personRepo.findById(id).orElse(null).getInsuranceContracts();
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
