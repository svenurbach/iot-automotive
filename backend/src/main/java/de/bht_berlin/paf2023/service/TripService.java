package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripService implements MeasurementObserver {
    private final TripRepo repository;
    private final MeasurementRepoSubject measurementRepo;
    private static TripMeasurementHandler tripMeasurementHandler;
    public TripHandlerStrategy tripHandlerStrategy;

    @Override
    public void updateMeasurement(Measurement newMeasurement) {
        if (this.tripHandlerStrategy != null && this.tripHandlerStrategy.getClass().getSimpleName().equals(
                "HandleSingleTripStrategy")) {
            this.tripHandlerStrategy.addData(newMeasurement);
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


    public void setTripMeasurementHandler(TripMeasurementHandler handler) {
        tripMeasurementHandler = handler;
    }

    public static void endTrip(Trip trip, LocationMeasurement endLocation, MeasurementRepoSubject measurementRepo,
                               TripRepo repository) {
        System.out.print("ending trip" + trip.getId() + endLocation.getTimestamp());
        if (endLocation.getTimestamp().getTime() < trip.getTrip_start().getTime()) {
            return;
        }
        trip.setTrip_end(endLocation.getTimestamp());
        trip.setEnd_longitude(endLocation.getLongitude());
        trip.setEnd_latitude(endLocation.getLatitude());
        endLocation.setTrip(trip);
        trip.finish(endLocation);
        measurementRepo.updateMeasurement(endLocation);
        repository.save(trip);
        tripMeasurementHandler.handle(trip);
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

    public Optional<Vehicle> findVehicleByTripId(long tripId) {
        return repository.findVehicleByTripId(tripId);
    }

    public List<Trip> findAllByVehicleId(long vehicleId, Date startDate, Date endDate) {
        return repository.findAllByVehicleIdAndDateRange(vehicleId, startDate, endDate);
    }

    public List<Trip> findAllByVehicleIdsAndDateRange(List<Long> vehicleId, Date startDate, Date endDate) {
        return repository.findAllByVehicleIdsAndDateRange(vehicleId, startDate, endDate);
    }

    public List<Trip> findAllByVehicleIds(List<Long> vehicleIds) {
        return repository.findAllByVehicleIds(vehicleIds);
    }

    public Optional<Measurement> findLatestMeasurementOfFirstUnfinishedTrip(long vehicleId) {
        return repository.findLatestMeasurementOfFirstUnfinishedTrip();
    }

    public Double getTotalDistanceForTrip(Trip trip) {
        Double totalDistanceInKilometers = trip.getDistance();
        if (totalDistanceInKilometers == null) {
            long tripId = trip.getId();
            List<Measurement> orginalList = measurementRepo.findMeasurementTypeInTrip(
                    "SpeedMeasurement", tripId);
            List<Measurement> filteredList = filterOutErrors(orginalList);
            totalDistanceInKilometers = getTotalDistance(filteredList);
            if (trip.getState() == Trip.TripState.FINISHED) {
                trip.setDistance(totalDistanceInKilometers);
                repository.save(trip);
            }
        }
        return totalDistanceInKilometers;
    }

    public Double getAverageSpeedForTrip(Trip trip) {
        Double averageSpeed = trip.getAverage_speed();
        if (averageSpeed == null) {
            long tripId = trip.getId();
            List<Measurement> orginalList = measurementRepo.findMeasurementTypeInTrip(
                    "SpeedMeasurement", tripId);
            List<Measurement> filteredList = filterOutErrors(orginalList);
            averageSpeed = getAverageSpeed(filteredList);
            if (trip.getState() == Trip.TripState.FINISHED) {
                trip.setAverage_speed(averageSpeed);
                repository.save(trip);
            }
        }
        return averageSpeed;

    }

    public Double getAverageSpeed(List<Measurement> measurements) {
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }

        // Conversion factor from milliseconds to hours
        double timeConversionFactor = 1.0 / (1000.0 * 60 * 60);

        double totalDistance = 0.0;
        long totalTimeMillis = 0;

        // Iterate through the measurements to calculate total distance and total time
        for (int i = 1; i < measurements.size(); i++) {
            SpeedMeasurement prevMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currMeasurement = (SpeedMeasurement) measurements.get(i);

            // Calculate time difference in milliseconds
            long timeDifferenceMillis = currMeasurement.getTimestamp().getTime() - prevMeasurement.getTimestamp().getTime();

            // Only consider measurements where the speed is not zero
            if (prevMeasurement.getSpeed() != 0) {
                // Convert time difference to hours
                double timeDifferenceHours = timeDifferenceMillis * timeConversionFactor;

                // Calculate distance covered in kilometers
                double distance = prevMeasurement.getSpeed() * timeDifferenceHours;
                totalDistance += distance;
                totalTimeMillis += timeDifferenceMillis;
            }
        }

        // Calculate average speed
        if (totalTimeMillis != 0) {
            // Convert total time to hours
            double totalTimeHours = totalTimeMillis * timeConversionFactor;
            return totalDistance / totalTimeHours;
        } else {
//            throw new ArithmeticException("Total time is zero");
            return 0.0;
        }
    }


    public Double getTotalDistance(List<Measurement> measurements) {
        double totalDistance = 0;
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }

        // Iterate through the measurements to calculate total distance
        for (int i = 1; i < measurements.size(); i++) {
            SpeedMeasurement prevMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currMeasurement = (SpeedMeasurement) measurements.get(i);

            // Convert time difference to hours
            double timeDifferenceHours =
                    (currMeasurement.getTimestamp().getTime() - prevMeasurement.getTimestamp().getTime()) / (1000.0 * 60 * 60);

            double distance = prevMeasurement.getSpeed() * timeDifferenceHours;

            // Only consider measurements where the speed is not zero
            if (prevMeasurement.getSpeed() != 0) {
                totalDistance += distance;
            }
        }

        return totalDistance;
    }

    public List<Measurement> filterOutErrors(List<Measurement> measurements) {
        return measurements.stream()
                .filter(measurement -> measurement.getIsError() == null || measurement.getIsError() == false)
                .collect(Collectors.toList());
    }

    public long calculateTripDurationInMillis(Trip trip) {
        return trip.getTrip_end().getTime() - trip.getTrip_start().getTime();
    }

//    public static void setAverageSpeedForTrip(Trip trip) {
//        Double average = getAverageSpeedForTrip(trip);
//        trip.setAverage_speed(average);
//    }
//
//    public static void setTotalDistance(Trip trip) {
//        Double total_distance = getTotalDistanceForTrip(trip);
//        trip.setTotal_distance(total_distance);
//    }
//
//    public static void setStatisticsForTrip(Trip trip, TripRepo repo) {
//        setAverageSpeedForTrip(trip);
//        setTotalDistance(trip);
//        repo.save(trip);
//    }
}
