package de.bht_berlin.paf2023.strategy;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TripHandlerStrategy {

    Trip startTrip(LocationMeasurement startLocation);

    void updateTrip(Trip trip, Measurement measurement);

    void endTrip(Trip trip, LocationMeasurement endLocation);

    void addData(List<Vehicle> vehicles);

    void addData(Measurement measurements);
}
