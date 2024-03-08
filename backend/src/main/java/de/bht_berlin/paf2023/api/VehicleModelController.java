package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/vehicleModel")
public class VehicleModelController {

    @Autowired
    private final VehicleService vehicleService;

    public VehicleModelController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "/{id}")
    public List<VehicleModel> getAllVehicleModels(@PathVariable Long id){
        return vehicleService.getAllVehicleModels();
    }

}
