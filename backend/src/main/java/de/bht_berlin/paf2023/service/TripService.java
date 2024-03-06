package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.component.HandleSingleTripStrategy;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
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

    public static void endTrip(Trip trip, LocationMeasurement endLocation, MeasurementRepoSubject measurementRepo,
                               TripRepo repository) {
        trip.setTrip_end(endLocation.getTimestamp());
        trip.setEnd_longitude(endLocation.getLongitude());
        trip.setEnd_latitude(endLocation.getLatitude());
        endLocation.setTrip(trip);
        trip.finish(endLocation);
        measurementRepo.updateMeasurement(endLocation);
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

    public List<Trip> getTripsByDateRange(Date start, Date end) {
        return repository.getTripsByDateRange(start, end);
    }

    public List<Trip> findAllByVehicleId(long vehicleId) {
        return repository.findAllByVehicleId(vehicleId);
    }

    public Optional<Measurement> findLatestMeasurementOfFirstUnfinishedTrip(long vehicleId) {
        return repository.findLatestMeasurementOfFirstUnfinishedTrip();
    }

    public Double avarageSpeed(List<Measurement> measurements) {
        double avarageSpeed = 0;
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }

        double totalDistance = 0.0;
        long totalTime = 0;

        // Calculate total distance and total time
        for (int i = 1; i < measurements.size(); i++) {
            SpeedMeasurement prevMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currMeasurement = (SpeedMeasurement) measurements.get(i);

            double distance =
                    prevMeasurement.getSpeed() * (currMeasurement.getTimestamp().getTime() - prevMeasurement.getTimestamp().getTime());
            totalDistance += distance;
            totalTime += (currMeasurement.getTimestamp().getTime() - prevMeasurement.getTimestamp().getTime());
        }

        // Calculate average speed
        if (totalTime != 0) {
            avarageSpeed = totalDistance / totalTime;
        } else {
            throw new ArithmeticException("Total time is zero");
        }
        return avarageSpeed;
    }

    public Double getTotalDistance(List<Measurement> measurements) {
        double getTotalDistance = 0;
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }
        for (int i = 1; i < measurements.size(); i++) {
            SpeedMeasurement prevMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currMeasurement = (SpeedMeasurement) measurements.get(i);

            getTotalDistance =
                    prevMeasurement.getSpeed() * (currMeasurement.getTimestamp().getTime() - prevMeasurement.getTimestamp().getTime());
        }
        if (getTotalDistance != 0) {
            return getTotalDistance;
        } else {
            throw new ArithmeticException("Total time is zero");
        }
    }
}
