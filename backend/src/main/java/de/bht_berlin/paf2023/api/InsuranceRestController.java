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

    @RequestMapping(path = "/find/{id}")
    public Insurance getInsurance(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id);
//        http://localhost:8080/api/insurance/find/2
    }

    @GetMapping(path = "/findAll")
    public List<Insurance> getAllTrips() {
        return insuranceService.getAllInsurances();
//        http://localhost:8080/api/insurance/findAll
    }
}
