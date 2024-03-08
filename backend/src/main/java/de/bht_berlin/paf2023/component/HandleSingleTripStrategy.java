package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HandleSingleTripStrategy implements TripHandlerStrategy {
    private final TripRepo repository;
    private final MeasurementRepoSubject measurementRepo;
    long timeBetweenTripsInMinutes = 30;

    public HandleSingleTripStrategy(TripRepo repository, MeasurementRepoSubject measurementRepo) {
        this.repository = repository;
        this.measurementRepo = measurementRepo;
    }

    @Override
    @Transactional
    public void addData(Measurement newMeasurement) {

        Vehicle vehicle = newMeasurement.getVehicle();
        Measurement lastMeasurement = measurementRepo.findLastMeasurementBeforeCurrent(vehicle.getId(), newMeasurement);
        Trip lastTrip = measurementRepo.findLastTripByVehicleId(vehicle.getId());

        if (lastTrip == null || lastMeasurement == null) {
            startNewTrip(newMeasurement);
        } else {
            long differenceInMillis =
                    newMeasurement.getTimestamp().getTime() - lastMeasurement.getTimestamp().getTime();
            long differenceInMinutes = differenceInMillis / (60 * 1000);
            if (differenceInMinutes > timeBetweenTripsInMinutes) {
                updateTrip(lastTrip, newMeasurement);
                LocationMeasurement lastLocation =
                        (LocationMeasurement) measurementRepo.findLastLocationMeasurementByVehicleId(vehicle.getId());
                endTrip(lastTrip, lastLocation);
                startNewTrip(newMeasurement);
            } else {
                updateTrip(lastTrip, newMeasurement);
            }
        }
    }

    private void startNewTrip(Measurement newMeasurement) {
        LocationMeasurement startLocation = null;
        if (newMeasurement.getMeasurementType().equals("LocationMeasurement")) {
            startLocation = (LocationMeasurement) newMeasurement;
            Trip trip = startTrip(startLocation);
            repository.save(trip);
            startLocation.setTrip(trip);
            measurementRepo.updateMeasurement(startLocation);
        }
    }

    @Override
    public Trip startTrip(LocationMeasurement startLocation) {
        return TripService.startTrip(startLocation, repository);
    }

    @Override
    public void updateTrip(Trip trip, Measurement measurement) {
        measurement.setTrip(trip);
        measurementRepo.updateMeasurement(measurement);
    }

    @Override
    public void endTrip(Trip trip, LocationMeasurement endLocation) {
        TripService.endTrip(trip, endLocation, measurementRepo, repository);
    }

    @Override
    public void addData(List<Vehicle> vehicleIds) {
    }
}