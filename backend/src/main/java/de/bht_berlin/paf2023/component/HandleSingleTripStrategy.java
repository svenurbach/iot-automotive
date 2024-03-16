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
        Trip currentTrip = measurementRepo.findLastTripByVehicleId(vehicle.getId());
        System.out.println("adding new measurement" + newMeasurement.getTimestamp());
        if (currentTrip == null || lastMeasurement == null) {
            startNewTrip(newMeasurement);
        } else {
            long differenceInMillis =
                    newMeasurement.getTimestamp().getTime() - lastMeasurement.getTimestamp().getTime();
            long differenceInMinutes = differenceInMillis / (60 * 1000);
            if (differenceInMinutes > timeBetweenTripsInMinutes) {
                updateTrip(currentTrip, newMeasurement);
//                LocationMeasurement lastLocation =
//                        (LocationMeasurement) measurementRepo.findLastLocationMeasurementByVehicleId(vehicle.getId());
                LocationMeasurement lastLocation =
                        (LocationMeasurement) measurementRepo.findLastLocationBeforeNewMeasurement(vehicle.getId(),
                                lastMeasurement);
                System.out.println("current trip from if" + currentTrip.getTrip_end());

                System.out.println("last measurement" + lastMeasurement.getTimestamp());
                System.out.println("time difference: " + differenceInMinutes);
                System.out.println("new measurement" + newMeasurement.getTimestamp());
                System.out.println("should finish here " + lastLocation.getTimestamp());
                endTrip(currentTrip, lastLocation);
                System.out.println("STarting new trip from adding method");
                startNewTrip(newMeasurement);
            } else {
                System.out.println("current trip from else" + currentTrip.getTrip_end());
                updateTrip(currentTrip, newMeasurement);
            }
        }
    }

    private void startNewTrip(Measurement newMeasurement) {
        System.out.print("Staring new trip " + newMeasurement.getTimestamp());

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