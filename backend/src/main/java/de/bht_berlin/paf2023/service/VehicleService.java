package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final VehicleModelRepo vehicleModelRepo;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo, VehicleModelRepo vehicleModelRepo) {
        this.vehicleRepo = vehicleRepo;
        this.vehicleModelRepo = vehicleModelRepo;
    }


    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    public List<VehicleModel> getAllVehicleModels(){
        return vehicleModelRepo.findAll();
    }


}
