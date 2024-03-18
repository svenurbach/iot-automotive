package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/vehicle")
public class VehicleController {
    @Autowired
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "/findAll")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @GetMapping(path = "/{id}")
    public Optional<Vehicle> getVehicle(@PathVariable Long id){
        return vehicleService.getVehicleDetail(id);
    }

    @GetMapping(path = "/{id}/measurementErrors")
    public List<Measurement> findAllMeasurementsFromVehicleWithError(@PathVariable Long id) {
        return vehicleService.findAllMeasurementsFromVehicleWithError(id);
    }
}
