package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.InsuranceContract;
import de.bht_berlin.paf2023.repo.ContractRepo;
import de.bht_berlin.paf2023.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/insurance")
public class InsuranceRestController {

    private final ContractRepo contractRepo;

    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceRestController(ContractRepo contractRepo, InsuranceService insuranceService) {
        this.contractRepo = contractRepo;
        this.insuranceService = insuranceService;
    }

    /**
     * Adds a new insurance contract.
     * @param insuranceContract The insurance contract to be added.
     * @return The added insurance contract.
     */
    @PostMapping(path = "/add")
    public InsuranceContract addInsuranceContract(@RequestBody InsuranceContract insuranceContract) {
        return insuranceService.addInsuranceContract(insuranceContract);
    // http://localhost:8080/api/insurance/add
    }

    /**
     * Deletes an insurance contract by its ID.
     * @param id The ID of the insurance contract to be deleted.
     * @return A message indicating the result of the deletion operation.
     */
    @DeleteMapping(path = "/delete/{id}")
    public String deleteInsuranceContract(@PathVariable Long id) {
        return insuranceService.deleteInsuranceContract(id);
    // http://localhost:8080/api/insurance/delete/2
    }

    /**
     * Retrieves an insurance contract by its ID.
     * @param id The ID of the insurance contract to be retrieved.
     * @return The insurance contract with the specified ID.
     */
    @GetMapping(path = "/find/{id}")
    public InsuranceContract getInsurance(@PathVariable Long id) {
        return insuranceService.getInsuranceContractById(id);
    // http://localhost:8080/api/insurance/find/2
    }

    /**
     * Retrieves all insurance contracts associated with a person.
     * @param id The ID of the person whose insurance contracts are to be retrieved.
     * @return A list of insurance contracts associated with the specified person.
     */
    @GetMapping(path = "/findByPerson/{id}")
    public List<InsuranceContract> getInsurancesByPerson(@PathVariable Long id) {
        return insuranceService.getInsurancesByPerson(id);
    // http://localhost:8080/api/insurance/findByPerson/1
    }

    /**
     * Retrieves the insurance contract associated with a car.
     * @param id The ID of the car.
     * @return The insurance contract associated with the specified car.
     */
    @GetMapping(path = "/findByCar/{id}")
    public InsuranceContract getInsuranceByCar(@PathVariable Long id) {
        return insuranceService.getInsuranceByCar(id);
    // http://localhost:8080/api/insurance/findByCar/1
    }

    /**
     * Retrieves all insurance contracts.
     * @return A list of all insurance contracts.
     */
    @GetMapping(path = "/findAll")
    public List<InsuranceContract> getAllContracts() {
        return insuranceService.getAllContracts();
    // http://localhost:8080/api/insurance/findAll
    }

    /**
     * Builds a new insurance contract.
     * @param request The request containing information to build the insurance contract.
     */
    @PostMapping(path = "/build")
    public void buildContract(@RequestBody InsuranceContract request) {
        InsuranceContract insuranceContract = new InsuranceContract.Builder()
                .setPolicyNumber(request.getPolicyNumber())
                .setDeductible(request.getDeductible())
                .setContractDistance(request.getContractDistance())
                .setContractPrice(request.getContractPrice())
                .setBegin(request.getBegin())
                .setVehicle(request.getVehicle())
                .setPolicyholder(request.getPolicyholder())
                .setInsurance(request.getInsurance())
                .build();

        contractRepo.save(insuranceContract);
        // http://localhost:8080/api/insurance/add
    }

}
