package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling operations related to vehicle models.
 * This class maps HTTP requests to corresponding service methods and returns appropriate responses.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/vehicleModel")
public class VehicleModelController {

    @Autowired
    private final VehicleService vehicleService;

    /**
     * Constructs a new VehicleModelController with the specified VehicleService.
     * @param vehicleService The service responsible for vehicle-related operations.
     */
    public VehicleModelController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Retrieves details of a specific vehicle model by its ID.
     * @param id ID of the vehicle model to retrieve.
     * @return The vehicle model corresponding to the given ID.
     */
    @GetMapping(path = "/{id}")
    public VehicleModel getVehicleModel(@PathVariable Long id){
        return vehicleService.getVehicleModel(id);
    }

}
