package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
//        todo add query to get trips from measurements
//        return positionStrategy.findLastPosition(Objects.requireNonNull(vehicleRepo.findById(id).orElse(null))
//                .getTrips());
        return null;

    }

}
