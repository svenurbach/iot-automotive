package de.bht_berlin.paf2023.strategy;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;

import java.util.List;

public interface PositionStrategy {
    List<Float> findLastPosition(List<Trip> trips);
}
