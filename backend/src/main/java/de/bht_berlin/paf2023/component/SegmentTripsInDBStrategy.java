package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SegmentTripsInDBStrategy implements TripHandlerStrategy {
    private final TripRepo repository;
    private final MeasurementRepoSubject measurementRepo;

    // set amount of time in minutes measurements must be apart in order to detect a new trip
    private final long timeBetweenTripsInMinutes = 30;

    public SegmentTripsInDBStrategy(TripRepo repository, MeasurementRepoSubject measurementRepo) {
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
        measurementRepo.updateMeasurement(measurement);
    }

    @Override
    public void endTrip(Trip trip, LocationMeasurement endLocation) {
        TripService.endTrip(trip, endLocation, measurementRepo, repository);
    }

    /**
     * segments all existing measurements into trips for given vehicles
     *
     * @param vehicles list of vehicles
     */
    @Override
    public void addData(List<Vehicle> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            List<Measurement> measurements = measurementRepo.findByVehicle(vehicles.get(i).getId());
            segmentTripBatches(measurements);
        }
    }

    /**
     * segments measurements into trips by timestamp as delimiting factor
     *
     * @param list list of measurements of one vehicle
     */
    public void segmentTripBatches(List<Measurement> list) {
        //sort list of measurements by timestamp
        Collections.sort(list, Comparator.comparing(Measurement::getTimestamp));

        // initializes new list of measurement list
        List<List<Measurement>> segmentedList = new ArrayList<>();

        // initializes new list to store indices of measurements which mark a new trip
        List<Integer> newTripIndices = new ArrayList<>();
        newTripIndices.add(0);

        // iterate  through list of measurements and check for amount of time between measurements
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                long differenceInMillis =
                        list.get(i).getTimestamp().getTime() - list.get(i - 1).getTimestamp().getTime();
                long differenceInMinutes = differenceInMillis / (60 * 1000);
                // detect new trip if time difference is bigger than defined amount. Add index to measurement to
                // indices list
                if (differenceInMinutes > this.timeBetweenTripsInMinutes) {
                    newTripIndices.add(i);
                }
            }
        }

        // iterate through indices list to segment measurements belonging to the same trip
        for (int i = 0; i < newTripIndices.size(); i++) {
            List<Measurement> measurements = new ArrayList<>();
            List<Integer> ints = new ArrayList<>();

            //determine start measurement for a trip
            int startingMeasurement = newTripIndices.get(i);

            //todo check if needed
            int endMeasurement = 0;

            //determine end measurement for a trip
            if (i < newTripIndices.size() - 1) {
                endMeasurement = newTripIndices.get(i + 1) - 1;
            } else {
                endMeasurement = list.size() - 1;
            }

            // add measurements into new list
            for (int j = startingMeasurement; j <= endMeasurement; j++) {
                measurements.add(list.get(j));

                //todo check if needed
                ints.add(Math.toIntExact(list.get(j).getId()));
            }
            // add new list to segmented list
            segmentedList.add(measurements);
        }

        // iterate  through segmented list and create trips for each segment
        for (int i = 0; i < segmentedList.size(); i++) {
            addEntireTrip(segmentedList.get(i));
        }
    }

    /**
     * create trip entity with given list
     *
     * @param measurementList list of measurements belonging to a trip
     */
    public void addEntireTrip(List<Measurement> measurementList) {

        // declare variables for start and end location
        LocationMeasurement startLocation = null;
        LocationMeasurement endLocation = null;

        // sort measurement list by ascending timestamp
        Collections.sort(measurementList, new Comparator<Measurement>() {
            @Override
            public int compare(Measurement measurement1, Measurement measurement2) {
                return measurement1.getTimestamp().compareTo(measurement2.getTimestamp());
            }
        });

        // iterate through sorted list to detect first location measurement for setting start location
        for (int i = 0; i < measurementList.size(); i++) {
            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                startLocation = (LocationMeasurement) measurementList.get(i);
                break;
            }
        }

        // iterate backwards through sorted list to detect last location measurement for setting end location
        for (int i = measurementList.size() - 1; i >= 0; i--) {
            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                endLocation = (LocationMeasurement) measurementList.get(i);
                break;
            }
        }

        if (startLocation != null) {
            // create trip entity and save it to repo
            Trip trip = startTrip(startLocation);
            repository.save(trip);

            // set trip on start location
            startLocation.setTrip(trip);
            measurementRepo.updateMeasurement(startLocation);

            // iterate through measurement list and set trip on each measurement
            for (int i = 0; i < measurementList.size(); i++) {
                updateTrip(trip, measurementList.get(i));
            }

            if (endLocation != null) {
                // set trip on end location
                repository.save(trip);
                endTrip(trip, endLocation);
                measurementRepo.updateMeasurement(endLocation);
            }
        }
    }

    @Override
    public void addData(Measurement measurements) {
    }
}