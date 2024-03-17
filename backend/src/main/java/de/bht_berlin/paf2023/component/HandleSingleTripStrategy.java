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

    // set amount of time in minutes measurements must be apart in order to detect a new trip
    private final long timeBetweenTripsInMinutes = 30;

    public HandleSingleTripStrategy(TripRepo repository, MeasurementRepoSubject measurementRepo) {
        this.repository = repository;
        this.measurementRepo = measurementRepo;
    }

    /**
     * create single measurement from scheduled readout and find trip it belongs to or start new trip
     *
     * @param newMeasurement measurement to be added to trip
     */
    @Override
    @Transactional
    public void addData(Measurement newMeasurement) {

        // get vehicle of measurement
        Vehicle vehicle = newMeasurement.getVehicle();

        // determine last measurement of vehicle
        Measurement lastMeasurement = measurementRepo.findLastMeasurementBeforeCurrent(vehicle.getId(), newMeasurement);

        // get the last current trip of a vehicle
        Trip currentTrip = measurementRepo.findLastTripByVehicleId(vehicle.getId());

        // start new trip if no trip and no last measurement found
        if (currentTrip == null || lastMeasurement == null) {
            startNewTrip(newMeasurement);
        } else {
            // calculate time difference between current measurement and last measurement
            long differenceInMillis =
                    newMeasurement.getTimestamp().getTime() - lastMeasurement.getTimestamp().getTime();
            long differenceInMinutes = differenceInMillis / (60 * 1000);

            // start start trip if time difference exceeds set max interval
            if (differenceInMinutes > this.timeBetweenTripsInMinutes) {
                updateTrip(currentTrip, newMeasurement);

                // find last location measurement of ongoing trip
                LocationMeasurement lastLocation =
                        (LocationMeasurement) measurementRepo.findLastLocationBeforeNewMeasurement(vehicle.getId(),
                                lastMeasurement);

                // end trip and set end time / end location
                endTrip(currentTrip, lastLocation);

                // start new trip with new measurement
                startNewTrip(newMeasurement);
            } else {
                // add new measurement to current trip if time difference does not exceed set interval
                updateTrip(currentTrip, newMeasurement);
            }
        }
    }

    /**
     * start new trip and set start location & start date
     *
     * @param newMeasurement new measurement to start new trip
     */
    private void startNewTrip(Measurement newMeasurement) {

        LocationMeasurement startLocation = null;

        // set start location and start new trip
        if (newMeasurement.getMeasurementType().equals("LocationMeasurement")) {
            startLocation = (LocationMeasurement) newMeasurement;
            Trip trip = startTrip(startLocation);
            repository.save(trip);
            // set trip on measurement
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