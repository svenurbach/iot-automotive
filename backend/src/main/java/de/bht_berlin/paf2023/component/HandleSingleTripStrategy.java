package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;

import java.util.List;

public class HandleSingleTripStrategy implements TripHandlerStrategy {

    @Override
    public Trip startTrip(LocationMeasurement startLocation, Vehicle vehicle) {
        return null;
    }

    @Override
    public void updateTrip(Trip trip, Measurement measurement) {

    }

    @Override
    public void endTrip(Trip trip, LocationMeasurement endLocation) {

    }

    @Override
    public void addData(List<Measurement> measurements) {

    }

    @Override
    public void addData(Measurement measurements) {

    }
}