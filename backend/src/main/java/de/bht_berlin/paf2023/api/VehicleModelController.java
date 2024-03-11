package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/vehicleModel")
public class VehicleModelController {

    @Autowired
    private final VehicleService vehicleService;

    public VehicleModelController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "/{id}")
    public VehicleModel getVehicleModel(@PathVariable Long id){
        return vehicleService.getVehicleModel(id);
    }

}
