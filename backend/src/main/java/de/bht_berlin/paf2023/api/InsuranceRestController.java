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

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    public InsuranceRestController(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
    }


    @PostMapping(path = "/add")
    public InsuranceContract addInsuranceContract(@RequestBody InsuranceContract insuranceContract) {
        return insuranceService.addInsuranceContract(insuranceContract);
    //    http://localhost:8080/api/insurance/add
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteInsuranceContract(@PathVariable Long id) {
        return insuranceService.deleteInsuranceContract(id);
        // http://localhost:8080/api/insurance/delete/2
    }

    @GetMapping(path = "/find/{id}")
    public InsuranceContract getInsurance(@PathVariable Long id) {
        return insuranceService.getInsuranceContractById(id);
//        http://localhost:8080/api/insurance/find/2
    }

    @GetMapping(path = "/findByPerson/{id}")
    public List<InsuranceContract> getInsurancesByPerson(@PathVariable Long id) {
        return insuranceService.getInsurancesByPerson(id);
//        http://localhost:8080/api/insurance/findByPerson/1
    }
    @GetMapping(path = "/findByCar/{id}")
    public InsuranceContract getInsuranceByCar(@PathVariable Long id) {
        return insuranceService.getInsuranceByCar(id);
//        http://localhost:8080/api/insurance/findByCar/1
    }

    @GetMapping(path = "/findAll")
    public List<InsuranceContract> getAllContracts() {
        return insuranceService.getAllContracts();
//        http://localhost:8080/api/insurance/findAll
    }

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
        //    http://localhost:8080/api/insurance/add
    }

}
