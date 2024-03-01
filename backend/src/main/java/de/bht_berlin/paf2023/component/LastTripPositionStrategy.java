package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class LastTripPositionStrategy implements PositionStrategy {

    private final VehicleRepo vehicleRepo;
    private final TripRepo tripRepo;

    public LastTripPositionStrategy(VehicleRepo vehicleRepo, TripRepo tripRepo) {
        this.vehicleRepo = vehicleRepo;
        this.tripRepo = tripRepo;
    }

    @Override
    // Trips anhand von "trip_end" sortieren und den letzten Trip ausgeben
    public List<Float> findLastPosition(Long carId) {
        Vehicle vehicle = vehicleRepo.findById(carId).orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        List<Trip> trips = tripRepo.findByMeasurements_Vehicle(vehicle);

        Comparator<Trip> tripEndComparator = Comparator.comparing(Trip::getTrip_end);
        trips.sort(tripEndComparator.reversed());
        Trip aktuellsteTrip = trips.get(0);

        List<Float> lastPosition = new ArrayList<>();
        lastPosition.add(aktuellsteTrip.getEnd_latitude());
        lastPosition.add(aktuellsteTrip.getEnd_longitude());
        return lastPosition;
    }
}

