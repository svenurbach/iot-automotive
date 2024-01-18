package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.repo.InsuranceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InsuranceService {
    private final InsuranceRepo repository;

    @Autowired
    public InsuranceService(InsuranceRepo repository) {
        this.repository = repository;
    }

    public List<Insurance> getAllInsurances() {
        return repository.findAll();
    }

    public Insurance getInsuranceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Insurance addInsurance(Insurance insurance) {
        return repository.save(insurance);
    }

    public Number getSpecifiedKiloMeters(Long id) {
//        return repository.findSpecifiedKiloMeters(id);
        return null;
    }

    public String deleteInsurance(Long id) {
        repository.deleteById(id);
        return "Insurance removed! " + id;
    }

}
