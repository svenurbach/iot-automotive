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


    @Override
    public void addData(List<Vehicle> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            List<Measurement> measurements = measurementRepo.findByVehicle(vehicles.get(i).getId());
            segmentTripBatches(measurements);
        }
    }

    public void segmentTripBatches(List<Measurement> list) {
        Collections.sort(list, Comparator.comparing(Measurement::getTimestamp));

        List<List<Measurement>> segmentedList = new ArrayList<>();
        List<Integer> newTripIndices = new ArrayList<>();

        newTripIndices.add(0);

        long timeBetweenTripsInMinutes = 30;

        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                long differenceInMillis =
                        list.get(i).getTimestamp().getTime() - list.get(i - 1).getTimestamp().getTime();
                long differenceInMinutes = differenceInMillis / (60 * 1000);
                if (differenceInMinutes > timeBetweenTripsInMinutes) {
                    newTripIndices.add(i);
                }
            }
        }
        for (int i = 0; i < newTripIndices.size(); i++) {
            List<Measurement> measurements = new ArrayList<>();
            List<Integer> ints = new ArrayList<>();

            int startingMeasurement = newTripIndices.get(i);
            int endMeasurement = 0;
            if (i < newTripIndices.size() - 1) {
                endMeasurement = newTripIndices.get(i + 1) - 1;
            } else {
                endMeasurement = list.size() - 1;
            }
            for (int j = startingMeasurement; j <= endMeasurement; j++) {
                measurements.add(list.get(j));
                ints.add(Math.toIntExact(list.get(j).getId()));
            }
            segmentedList.add(measurements);
        }
        for (int i = 0; i < segmentedList.size(); i++) {
            addEntireTrip(segmentedList.get(i));
        }
    }

    @Override
    public void addData(Measurement measurements) {
    }

    public void addEntireTrip(List<Measurement> measurementList) {
        LocationMeasurement startLocation = null;
        LocationMeasurement endLocation = null;
        Collections.sort(measurementList, new Comparator<Measurement>() {
            @Override
            public int compare(Measurement measurement1, Measurement measurement2) {
                return measurement1.getTimestamp().compareTo(measurement2.getTimestamp());
            }
        });
        for (int i = 0; i < measurementList.size(); i++) {
            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                startLocation = (LocationMeasurement) measurementList.get(i);
                break;
            }
        }
        for (int i = measurementList.size() - 1; i >= 0; i--) {
            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                endLocation = (LocationMeasurement) measurementList.get(i);
                break;
            }
        }
        if (startLocation != null) {
            Trip trip = startTrip(startLocation);
            repository.save(trip);
            startLocation.setTrip(trip);
            measurementRepo.updateMeasurement(startLocation);
            for (int i = 0; i < measurementList.size(); i++) {
                updateTrip(trip, measurementList.get(i));
            }
            if (endLocation != null) {
                repository.save(trip);
                endTrip(trip, endLocation);
                measurementRepo.updateMeasurement(endLocation);
            }
        }
    }
}