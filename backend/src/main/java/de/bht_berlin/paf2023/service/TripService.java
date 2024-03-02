package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.component.HandleSingleTripStrategy;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TripService implements MeasurementObserver {
    private final TripRepo repository;
    private final MeasurementRepoSubject measurementRepo;
    public TripHandlerStrategy tripHandlerStrategy;

    @Override
    public void updateMeasurement(Measurement newMeasurement) {
        if (this.tripHandlerStrategy == null) {
            System.out.println("No strategy deployed yet");
        } else {
            if (this.tripHandlerStrategy.getClass().getSimpleName().equals("HandleSingleTripStrategy")) {
                this.tripHandlerStrategy.addData(newMeasurement);
                System.out.println(this.tripHandlerStrategy.getClass().getSimpleName());
                System.out.println(newMeasurement);
            } else {
                System.out.println(this.tripHandlerStrategy.getClass().getSimpleName());
            }
        }
    }

    public void changeTripHandlerStrategy(TripHandlerStrategy tripHandlerStrategy) {
        this.tripHandlerStrategy = tripHandlerStrategy;
    }

    @Autowired
    public TripService(TripRepo repository, MeasurementRepoSubject measurementRepo) {
        this.repository = repository;
        this.measurementRepo = measurementRepo;
        measurementRepo.addObserver(this);
    }

    public void segmentTripBatches(Vehicle vehicle) {
        List<Measurement> measurements = measurementRepo.findByVehicle(vehicle.getId());
        tripHandlerStrategy.addData(measurements);
    }

    public static void endTrip(Trip trip, LocationMeasurement endLocation, MeasurementRepoSubject measurementRepo,
                               TripRepo repository) {
        trip.setTrip_end(endLocation.getTimestamp());
        trip.setEnd_longitude(endLocation.getLongitude());
        trip.setEnd_latitude(endLocation.getLatitude());
        endLocation.setTrip(trip);
        trip.finish(endLocation);
        measurementRepo.addMeasurement(endLocation);
        repository.save(trip);
    }

    public static Trip startTrip(LocationMeasurement startLocation, TripRepo repository) {
        Trip trip = new Trip();
        trip.start(startLocation);
        trip.setTrip_start(startLocation.getTimestamp());
        trip.setStart_longitude(startLocation.getLongitude());
        trip.setStart_latitude(startLocation.getLatitude());
        repository.save(trip);
        return trip;
    }

    public List<Trip> getAllTrips() {
        return repository.findAll();
    }


}
