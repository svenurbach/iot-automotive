package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling vehicle-related operations.
 * This class is responsible for mapping HTTP requests to corresponding service methods
 * and returning appropriate responses.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/vehicle")
public class VehicleController {
    @Autowired
    private final VehicleService vehicleService;

    /**
     * Constructs a new VehicleController with the specified VehicleService.
     * @param vehicleService The service responsible for vehicle-related operations.
     */
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Retrieves all vehicles.
     * @return A list of all vehicles.
     */
    @GetMapping(path = "/findAll")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    /**
     * Retrieves details of a specific vehicle by its ID.
     * @param id ID of the vehicle to retrieve.
     * @return An Optional containing the vehicle details if found
     */
    @GetMapping(path = "/{id}")
    public Optional<Vehicle> getVehicle(@PathVariable Long id){
        return vehicleService.getVehicleDetail(id);
    }

    /**
     * Retrieves all measurements from a specific vehicle that have errors.
     * @param id ID of the vehicle.
     * @return A list of measurements with errors associated with the specified vehicle.
     */
    @GetMapping(path = "/{id}/measurementErrors")
    public List<Measurement> findAllMeasurementsFromVehicleWithError(@PathVariable Long id) {
        return vehicleService.findAllMeasurementsFromVehicleWithError(id);
    }
}
