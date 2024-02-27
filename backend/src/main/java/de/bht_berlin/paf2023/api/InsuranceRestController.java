package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/insurance")
public class InsuranceRestController {

    @Autowired
    private InsuranceService insuranceService;

    @PostMapping(path = "/add")
    public Insurance addInsurance(@RequestBody Insurance insurance) {
        return insuranceService.addInsurance(insurance);
    //    http://localhost:8080/api/insurance/add
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteInsurance(@PathVariable Long id) {
        return insuranceService.deleteInsurance(id);
    }

    @RequestMapping(path = "/find/{id}")
    public Insurance getInsurance(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id);
//        http://localhost:8080/api/insurance/find/2
    }

    @RequestMapping(path = "/findByPerson/{id}")
    public Insurance getInsurancesByPerson(@PathVariable Long id) {
        return insuranceService.getInsurancesByPerson(id);
//        http://localhost:8080/api/insurance/findByPerson/2
    }

    @GetMapping(path = "/findAll")
    public List<Insurance> getAllInsurances() {
        return insuranceService.getAllInsurances();
//        http://localhost:8080/api/insurance/findAll
    }
}