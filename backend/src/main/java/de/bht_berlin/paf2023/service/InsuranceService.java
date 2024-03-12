package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.InsuranceContract;
import de.bht_berlin.paf2023.repo.ContractRepo;
import de.bht_berlin.paf2023.repo.PersonRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

/**
 * Service class for managing insurance contracts.
 */
@Service
public class InsuranceService {
    private final ContractRepo contractRepo;
    private final PersonRepo personRepo;
    private final VehicleRepo vehicleRepo;

    /**
     * Constructs an InsuranceService with the required repositories.
     *
     * @param contractRepo The ContractRepo for accessing insurance contracts.
     * @param personRepo   The PersonRepo for accessing person-related data.
     * @param vehicleRepo  The VehicleRepo for accessing vehicle-related data.
     */
    @Autowired
    public InsuranceService(ContractRepo contractRepo, PersonRepo personRepo, VehicleRepo vehicleRepo) {
        this.contractRepo = contractRepo;
        this.personRepo = personRepo;
        this.vehicleRepo = vehicleRepo;
    }

    /**
     * Retrieves all insurance contracts.
     *
     * @return A list of all insurance contracts.
     */
    public List<InsuranceContract> getAllContracts() {
        return contractRepo.findAll();
    }

    /**
     * Retrieves an insurance contract by its ID.
     *
     * @param id The ID of the insurance contract to retrieve.
     * @return The insurance contract with the specified ID, or null if not found.
     */
    public InsuranceContract getInsuranceContractById(Long id) {
        return contractRepo.findById(id).orElse(null);
    }

    /**
     * Retrieves the insurance contract associated with a specific car.
     *
     * @param id The ID of the car.
     * @return The insurance contract associated with the specified car, or null if the car is not found or does not have an insurance contract.
     */
    public InsuranceContract getInsuranceByCar(Long id) {
        return Objects.requireNonNull(vehicleRepo.findById(id).orElse(null)).getInsuranceContract();
    }

    /**
     * Retrieves all insurance contracts associated with a specific person.
     *
     * @param id The ID of the person.
     * @return A list of insurance contracts associated with the specified person, or null if the person is not found.
     */
    public List<InsuranceContract> getInsurancesByPerson(Long id) {
        return Objects.requireNonNull(personRepo.findById(id).orElse(null)).getInsuranceContracts();
    }

    /**
     * Adds a new insurance contract.
     *
     * @param insuranceContact The insurance contract to add.
     * @return The added insurance contract.
     */
    public InsuranceContract addInsuranceContract(InsuranceContract insuranceContact) {
        return contractRepo.save(insuranceContact);
    }

    /**
     * Deletes an insurance contract by its ID.
     *
     * @param id The ID of the insurance contract to delete.
     * @return A message indicating the success of the deletion.
     */
    public String deleteInsuranceContract(Long id) {
        contractRepo.deleteById(id);
        return "InsuranceContract #" + id + " deleted";
    }
}
