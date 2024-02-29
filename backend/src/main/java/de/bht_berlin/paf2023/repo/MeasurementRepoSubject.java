package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasurementRepoSubject {

    private List<MeasurementObserver> observers = new ArrayList<>();

    @Autowired
    private MeasurementRepo measurementRepo;

    public void addObserver(MeasurementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MeasurementObserver observer) {
        observers.remove(observer);
    }

    public void addMeasurement(Measurement measurement) {
        measurementRepo.save(measurement);
        notifyObservers(measurement);
    }

    private void notifyObservers(Measurement newMeasurement) {
        for (MeasurementObserver observer : observers) {
            observer.updateMeasurement(newMeasurement);
        }
    }

}
