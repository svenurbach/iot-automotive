package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepo repository;

    @Autowired
    public VehicleService(VehicleRepo repository) {
        this.repository = repository;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public EndLocationMeasurement addEndLocationMeasurement(EndLocationMeasurement endLocationMeasurement) {
//        return repository.save(endLocationMeasurement);
        return null;
    }

    public EndLocationMeasurement getLastLocation() {
//        return repository.findLastLocation();
        return null;
    }

    public Number getKiloMeters() {
//        return repository.findKiloMeters();
        return null;
    }

}
