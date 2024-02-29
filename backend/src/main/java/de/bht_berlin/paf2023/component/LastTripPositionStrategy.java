package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class LastTripPositionStrategy implements PositionStrategy {

    @Override
    // Trips anhand von "trip_end" sortieren und den letzten Trip ausgeben
    public List<Float> findLastPosition(List<Trip> trips) {
        Comparator<Trip> tripEndComparator = Comparator.comparing(Trip::getTrip_end);
        trips.sort(tripEndComparator.reversed());
        Trip aktuellsteTrip = trips.get(0);

        List<Float> lastPosition = new ArrayList<>();
        lastPosition.add(aktuellsteTrip.getEnd_latitude());
        lastPosition.add(aktuellsteTrip.getEnd_longitude());
        return lastPosition;
    }
}

