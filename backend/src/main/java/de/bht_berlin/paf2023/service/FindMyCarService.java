package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMyCarService {
    private PositionStrategy positionStrategy;

    @Autowired
    public FindMyCarService(@Qualifier("lastTripPositionStrategy") PositionStrategy positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    public void setPositionStrategy(PositionStrategy positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    public List<Float> getLastCarPosition(Long carId) {
        return this.positionStrategy.findLastPosition(carId);
    }

}
