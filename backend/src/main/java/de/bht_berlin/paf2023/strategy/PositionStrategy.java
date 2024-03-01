package de.bht_berlin.paf2023.strategy;

import java.util.List;

public interface PositionStrategy {
    List<Float> findLastPosition(Long carId);
}
