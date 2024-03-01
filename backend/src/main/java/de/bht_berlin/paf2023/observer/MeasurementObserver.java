package de.bht_berlin.paf2023.observer;

import de.bht_berlin.paf2023.entity.Measurement;

public interface MeasurementObserver {
    void updateMeasurement(Measurement newMeasurement);
}
