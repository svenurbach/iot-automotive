package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for finding the last position of a car using a specified PositionStrategy.
 */
@Service
public class FindMyCarService {
    private PositionStrategy positionStrategy;

    /**
     * Constructs a FindMyCarService with the specified PositionStrategy.
     *
     * @param positionStrategy The PositionStrategy to be used for finding the last position.
     */
    @Autowired
    public FindMyCarService(@Qualifier("lastTripPositionStrategy") PositionStrategy positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    /**
     * Sets the PositionStrategy to be used for finding the last position.
     *
     * @param positionStrategy The PositionStrategy to be set.
     */
    public void setPositionStrategy(PositionStrategy positionStrategy) {
        this.positionStrategy = positionStrategy;
    }

    /**
     * Retrieves the last position of a car identified by the given carId.
     *
     * @param carId The identifier of the car.
     * @return A List containing the latitude and longitude of the last position.
     */
    public List<Float> getLastCarPosition(Long carId) {
        return this.positionStrategy.findLastPosition(carId);
    }

}
