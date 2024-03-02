package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;

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
    public Trip startTrip(LocationMeasurement startLocation) {
        return TripService.startTrip(startLocation, repository);
    }

    @Override
    public void updateTrip(Trip trip, Measurement measurement) {
        measurement.setTrip(trip);
        measurementRepo.addMeasurement(measurement);
    }

    @Override
    public void endTrip(Trip trip, LocationMeasurement endLocation) {
        TripService.endTrip(trip, endLocation, measurementRepo, repository);
    }

    @Override
    public void addData(Measurement newMeasurement) {
        Vehicle vehicle = newMeasurement.getVehicle();
        Measurement lastMeasurement = measurementRepo.findLastMeasurementByVehicleId(vehicle.getId());
        Trip currentTrip = repository.getById(newMeasurement.getTrip().getId());
        long differenceInMillis =
                newMeasurement.getTimestamp().getTime() - lastMeasurement.getTimestamp().getTime();
        long differenceInMinutes = differenceInMillis / (60 * 1000);
        if (differenceInMinutes > timeBetweenTripsInMinutes) {
            LocationMeasurement lastLocation =
                    (LocationMeasurement) measurementRepo.findLastLocationMeasurementByVehicleId(vehicle.getId());
            currentTrip.finish(lastLocation);
            if (newMeasurement.getMeasurementType() == "LocationMeasurement") {
                LocationMeasurement startLocation = (LocationMeasurement) newMeasurement;
                Trip newTrip = startTrip(startLocation);
                updateTrip(newTrip, startLocation);
            }
        } else {
            updateTrip(currentTrip, newMeasurement);
        }
    }


    @Override
    public void addData(List<Measurement> measurements) {
    }
}