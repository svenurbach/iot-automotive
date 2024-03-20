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

    /**
     * Retrieves all vehicles associated with a person.
     * @param id The ID of the person whose vehicles are to be retrieved.
     * @return A list of vehicles associated with the specified person.
     */
    @GetMapping(path = "/findByPerson/{id}")
    public List<Vehicle> getInsurancesByPerson(@PathVariable Long id) {
        return vehicleService.getVehiclesByPerson(id);
    // http://localhost:8080/api/vehicle/findByPerson/1
    }

    /**
     * Retrieves the vehicle associated with a particular insurance.
     * @param id The ID of the insurance.
     * @return The vehicle associated with the specified insurance.
     */
    @GetMapping(path = "/findByInsurance/{id}")
    public Vehicle getCarByIncurance(@PathVariable Long id) {
        return vehicleService.getCarByIncurance(id);
    // http://localhost:8080/api/vehicle/findByInsurance/1
    }
}