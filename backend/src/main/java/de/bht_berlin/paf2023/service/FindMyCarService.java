package de.bht_berlin.paf2023.service;

//import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMyCarService {
    private final VehicleRepo vehicleRepo;
    private final PositionStrategy positionStrategy;

    @Autowired
    public FindMyCarService(VehicleRepo vehicleRepo, PositionStrategy positionStrategy) {
        this.vehicleRepo = vehicleRepo;
        this.positionStrategy = positionStrategy;
    }

    public List<Float> getLastPositionByCar(Long id) {
        return positionStrategy.findLastPosition(vehicleRepo.getOne(id).getTrips());
    }

}
