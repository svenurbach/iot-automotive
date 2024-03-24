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

    @Autowired
    public TripService(TripRepo repository, MeasurementRepoSubject measurementRepo) {
        this.repository = repository;
        this.measurementRepo = measurementRepo;
        measurementRepo.addObserver(this);
    }

    /**
     * method which gets called by measurement observer when detecting a new measurement entity being created.
     * This method handles continous read outs from a csv file by scheduler and handles measurement data.
     *
     * @param newMeasurement newly created measurement
     */
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

    public void setTripMeasurementHandler(TripMeasurementHandler handler) {
        tripMeasurementHandler = handler;
    }

    /**
     * end trip with given measurement
     *
     * @param trip            trip which is being ended
     * @param endLocation     location measurement which is used for ending trip
     * @param measurementRepo measurement repo
     * @param repository      trip repo
     */
    public static void endTrip(Trip trip, LocationMeasurement endLocation, MeasurementRepoSubject measurementRepo,
                               TripRepo repository) {
        // safe guard for ensuring that trip end time is always after than trip start
        if (endLocation.getTimestamp().getTime() < trip.getTrip_start().getTime()) {
            return;
        }
        // set trip end date and end location
        trip.setTrip_end(endLocation.getTimestamp());
        trip.setEnd_longitude(endLocation.getLongitude());
        trip.setEnd_latitude(endLocation.getLatitude());
        trip.finish(endLocation);

        // update measurement
        endLocation.setTrip(trip);
        measurementRepo.updateMeasurement(endLocation);
        repository.save(trip);

        // call trip measurement handler to analyze measurements for errors
        tripMeasurementHandler.handle(trip);
    }

    /**
     * create trip entity with given measurement
     *
     * @param startLocation location measurement which is used for starting trip
     * @param repository    trip repo
     * @return created trip entity
     */
    public static Trip startTrip(LocationMeasurement startLocation, TripRepo repository) {
        // create trip entity and set start location & start time
        Trip trip = new Trip();
        trip.start(startLocation);
        trip.setTrip_start(startLocation.getTimestamp());
        trip.setStart_longitude(startLocation.getLongitude());
        trip.setStart_latitude(startLocation.getLatitude());

        // save trip to repo
        repository.save(trip);
        return trip;
    }

    /**
     * get traveled distance of a given trip
     *
     * @param trip trip the distance should be retrieved for
     * @return traveld distance as double
     */
    public Double getTotalDistanceForTrip(Trip trip) {
        // retrieve saved distance on trip entity
        Double totalDistanceInKilometers = trip.getDistance();

        // if distance is null, it is not calculated yet
        if (totalDistanceInKilometers == null) {
            //get trip id
            long tripId = trip.getId();

            // find all speed measurements which belong to this trip
            List<Measurement> orginalList = measurementRepo.findMeasurementTypeInTrip(
                    "SpeedMeasurement", tripId);

            // filter all out all measurements flagged as error
            List<Measurement> filteredList = filterOutErrors(orginalList);

            // call calculation method and pass filtered pass as argument

            // sort filtered list by timestamp
            Collections.sort(filteredList, Comparator.comparing(Measurement::getTimestamp));

            totalDistanceInKilometers = getTotalDistance(filteredList);

            // store calculated distance on trip entity if trip is finished
            if (trip.getState() == Trip.TripState.FINISHED && totalDistanceInKilometers > 0) {
                trip.setDistance(totalDistanceInKilometers);
                repository.save(trip);
            }
        }
        return totalDistanceInKilometers;
    }

    /**
     * get average speed of a given trip
     *
     * @param trip trip the average speed should be retrieved for
     * @return average speed as double
     */
    public Double getAverageSpeedForTrip(Trip trip) {
        // retrieve saved average speed on trip entity
        Double averageSpeed = trip.getAverage_speed();

        if (averageSpeed == null) {

            //get trip id
            long tripId = trip.getId();

            // find all speed measurements which belong to this trip
            List<Measurement> orginalList = measurementRepo.findMeasurementTypeInTrip(
                    "SpeedMeasurement", tripId);

            // filter all out all measurements flagged as error
            List<Measurement> filteredList = filterOutErrors(orginalList);

            // sort filtered list by timestamp
            Collections.sort(filteredList, Comparator.comparing(Measurement::getTimestamp));

            // call calculation method and pass filtered pass as argument
            averageSpeed = getAverageSpeed(filteredList);

            // store calculated average speed on trip entity if trip is finished
            if (trip.getState() == Trip.TripState.FINISHED && averageSpeed > 0) {
                trip.setAverage_speed(averageSpeed);
                repository.save(trip);
            }
        }
        return averageSpeed;
    }

    /**
     * calculate average speed for list of measurements
     *
     * @param measurements list of measurements
     * @return average speed as double
     */
    public Double getAverageSpeed(List<Measurement> measurements) {

        // throw exception of list is empty
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }

        // set conversion factor from milliseconds to hours
        double timeConversionFactor = 1.0 / (1000.0 * 60 * 60);

        // initialize working variables
        double totalDistance = 0.0;
        long totalTimeMillis = 0;

        // Iterate through the measurements to calculate total distance and total time by using a current and a
        // previous measurement
        for (int i = 1; i < measurements.size(); i++) {

            // cast current and previous measurements into speed measurements
            SpeedMeasurement previousMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currentMeasurement = (SpeedMeasurement) measurements.get(i);

            // Calculate time difference between current and previous measurements in milliseconds
            long timeDifferenceMillis =
                    currentMeasurement.getTimestamp().getTime() - previousMeasurement.getTimestamp().getTime();

            // ignore measurements with speed 0
            if (previousMeasurement.getSpeed() != 0) {
                // convert time difference to hours
                double timeDifferenceHours = timeDifferenceMillis * timeConversionFactor;

                // calculate distance traveled between current measurement and previous measurement
                double distance = previousMeasurement.getSpeed() * timeDifferenceHours;

                // add to sums of total distance and total time
                totalDistance += distance;
                totalTimeMillis += timeDifferenceMillis;
            }
        }

        // ignore if trip total time is 0
        if (totalTimeMillis != 0) {
            // convert total time to hours
            double totalTimeHours = totalTimeMillis * timeConversionFactor;

            // return calculated average speed
            return totalDistance / totalTimeHours;
        } else {
            // return 0 if total trip time is 0
            return 0.0;
        }
    }

    /**
     * calculate total distance for a given list of measurements
     *
     * @param measurements list of measurements
     * @return total distance as double
     */
    public Double getTotalDistance(List<Measurement> measurements) {
        double totalDistance = 0;
        if (measurements == null || measurements.isEmpty()) {
            throw new IllegalArgumentException("Measurement list is null or empty");
        }

        // Iterate through the measurements to calculate total distance by addind the distance between two measurements
        for (int i = 1; i < measurements.size(); i++) {

            // cast previous and current to speed measurement
            SpeedMeasurement previousMeasurement = (SpeedMeasurement) measurements.get(i - 1);
            SpeedMeasurement currentMeasurement = (SpeedMeasurement) measurements.get(i);

            // Calculate time difference between current and previous measurements in hours
            double timeDifferenceHours =
                    (currentMeasurement.getTimestamp().getTime() - previousMeasurement.getTimestamp().getTime()) / (1000.0 * 60 * 60);

            // calculate distance by multiplying speed with time
            double distance = previousMeasurement.getSpeed() * timeDifferenceHours;

            // check for speed not 0
            if (previousMeasurement.getSpeed() != 0) {

                // add distance to sum
                totalDistance += distance;
            }
        }

        return totalDistance;
    }

    /**
     * receives a list of measurements and filters our errors
     *
     * @param measurements list of measurements
     * @return filtered list of measurements
     */
    public List<Measurement> filterOutErrors(List<Measurement> measurements) {
        return measurements.stream()
                .filter(measurement -> measurement.getIsError() == null || !measurement.getIsError())
                .collect(Collectors.toList());
    }

    /**
     * passing method calls from trip controller to repo
     */

    public List<Trip> getAllTrips() {
        return repository.findAll();
    }

    public Optional<Vehicle> findVehicleByTripId(long tripId) {
        return repository.findVehicleByTripId(tripId);
    }

    public List<Trip> findAllByVehicleIdsAndDateRange(List<Long> vehicleId, Date startDate, Date endDate) {
        return repository.findAllByVehicleIdsAndDateRange(vehicleId, startDate, endDate);
    }

    public List<Trip> findAllByVehicleIds(List<Long> vehicleIds) {
        return repository.findAllByVehicleIds(vehicleIds);
    }

}
