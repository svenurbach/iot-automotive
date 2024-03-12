package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/vehicle")
public class VehicleRestController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    public VehicleRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "/findByPerson/{id}")
    public List<Vehicle> getInsurancesByPerson(@PathVariable Long id) {
        return vehicleService.getVehiclesByPerson(id);
//        http://localhost:8080/api/vehicle/findByPerson/1
    }
}