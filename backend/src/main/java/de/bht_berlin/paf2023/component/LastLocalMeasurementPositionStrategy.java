package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LastLocalMeasurementPositionStrategy implements PositionStrategy {

    private final VehicleRepo vehicleRepo;

    public LastLocalMeasurementPositionStrategy(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    // Find newest LocationMeasurement for car
    public List<Float> findLastPosition(Long carId) {
        Vehicle vehicle = vehicleRepo.findById(carId).orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        List<Measurement> locationMeasurements = vehicle.getMeasurements()
                .stream()
                .filter(measurement -> measurement instanceof LocationMeasurement)
                .sorted(Comparator.comparing(Measurement::getTimestamp).reversed())
                .collect(Collectors.toList());

        LocationMeasurement lastLocation = (LocationMeasurement) locationMeasurements.get(0);//
        List<Float> lastPosition = new ArrayList<>();
        lastPosition.add(lastLocation.getLatitude());
        lastPosition.add(lastLocation.getLongitude());
        return lastPosition;
    }
}

