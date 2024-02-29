package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.InsuranceContract;
import de.bht_berlin.paf2023.repo.ContractRepo;
import de.bht_berlin.paf2023.repo.PersonRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class InsuranceService {
    private final ContractRepo contractRepo;
    private final PersonRepo personRepo;
    private final VehicleRepo vehicleRepo;

    @Autowired
    public InsuranceService(ContractRepo contractRepo, PersonRepo personRepo, VehicleRepo vehicleRepo) {
        this.contractRepo = contractRepo;
        this.personRepo = personRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public List<InsuranceContract> getAllContracts() {
        return contractRepo.findAll();
    }

    public InsuranceContract getInsuranceContractById(Long id) {
        return contractRepo.findById(id).orElse(null);
    }

    public InsuranceContract getInsuranceByCar(Long id) {
        return Objects.requireNonNull(vehicleRepo.findById(id).orElse(null)).getInsuranceContract();
    }

    public List<InsuranceContract> getInsurancesByPerson(Long id) {
        return Objects.requireNonNull(personRepo.findById(id).orElse(null)).getInsuranceContracts();
    }

    public InsuranceContract addInsuranceContract(InsuranceContract insuranceContact) {
        return contractRepo.save(insuranceContact);
    }

    public String deleteInsuranceContract(Long id) {
        contractRepo.deleteById(id);
        return "InsuranceContract #" + id + " deleted";
    }

}
