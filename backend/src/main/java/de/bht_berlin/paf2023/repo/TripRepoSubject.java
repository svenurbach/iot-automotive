package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import de.bht_berlin.paf2023.observer.TripObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TripRepoSubject {

    private List<TripObserver> observers = new ArrayList<>();

    @Autowired
    private TripRepo tripRepo;

    public void addObserver(TripObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TripObserver observer) {
        observers.remove(observer);
    }

    public boolean isTripFinished(Trip trip){
        return true;
    }

}
