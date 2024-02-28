package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TripService {
    private final TripRepo repository;
    private final MeasurementRepo measurementRepo;


    @Autowired
    public TripService(TripRepo repository, MeasurementRepo measurementRepo) {
        this.repository = repository;
        this.measurementRepo = measurementRepo;

    }

    public void segmentDataIntoTrips(Vehicle vehicle) {
        List<Measurement> list = measurementRepo.findByVehicle(vehicle.getId());
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
//                    System.out.println(list.get(i - 1).getTimestamp() + " –-- " + list.get(i).getTimestamp() + ": " + differenceInMinutes);
                }
            }
        }
        System.out.println(newTripIndices);

        for (int i = 0; i < newTripIndices.size(); i++) {
//            boolean isEntireTrip = false;
            List<Measurement> measurements = new ArrayList<>();
            List<Integer> ints = new ArrayList<>();

            int startingMeasurement = newTripIndices.get(i);
            int endMeasurement = 0;
            if (i < newTripIndices.size() - 1) {
                endMeasurement = newTripIndices.get(i + 1) - 1;
//                isEntireTrip = true;
            } else {
                System.out.println("else list size");
                endMeasurement = list.size() - 1;
                System.out.println("endMeasurement:" + endMeasurement);
                System.out.println("list size: " + list.size());

            }
            for (int j = startingMeasurement; j < endMeasurement; j++) {
                measurements.add(list.get(j));
                ints.add(Math.toIntExact(list.get(j).getId()));
            }
            segmentedList.add(measurements);

        }
        System.out.println("segmentedList.get(i).get(j)");
        System.out.println(segmentedList.size());

        for (int i = 0; i < segmentedList.size(); i++) {
            System.out.println("trip: " + i);
            for (int j = 0; j < segmentedList.get(i).size(); j++) {
                System.out.println(segmentedList.get(i).get(j).getId());
            }


            addEntireTrip(segmentedList.get(i));
        }
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
//            System.out.println(measurementList.get(i).getMeasurementType());
            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                startLocation = (LocationMeasurement) measurementList.get(i);
                break;
            }
        }
        System.out.println(measurementList.toString());
        for (int i = measurementList.size() - 1; i >= 0; i--) {
            System.out.println(measurementList.get(i).getId() + ": " + measurementList.get(i).getMeasurementType());

            if (measurementList.get(i).getMeasurementType().equals("LocationMeasurement")) {
                endLocation = (LocationMeasurement) measurementList.get(i);
                System.out.println("found endlocation" + endLocation.getLongitude() + " date: " + endLocation.getTimestamp());
                break;
            }
        }
        if (startLocation != null) {
            Trip trip = startTrip(startLocation, measurementList.get(0).getVehicle());
            for (int i = 0; i < measurementList.size(); i++) {
                updateTrip(trip, measurementList.get(i));
            }
            if (endLocation != null) {
                endTrip(trip, endLocation);
            }
        }


    }

    public Trip startTrip(LocationMeasurement startLocation,
                          Vehicle vehicle) {
        Trip trip = new Trip();
        trip.setTrip_start(startLocation.getTimestamp());
        trip.setStartLocation(startLocation);
        trip.setVehicle(vehicle);

        return repository.save(trip);
    }

    public void updateTrip(Trip trip, Measurement measurement) {
        trip.addMeasurement(measurement);

    }

    public void endTrip(Trip trip, LocationMeasurement endLocation) {
        trip.setTrip_end(endLocation.getTimestamp());
        trip.setEndLocation(endLocation);
        repository.save(trip);
    }

//    public LocationMeasurement addEndLocationMeasurement(LocationMeasurement endLocationMeasurement) {
//        return null;
//    }

//    public LocationMeasurement getLastLocation() {
//        return null;
//    }

//    public Number getKiloMeters() {
//        return null;
//    }

}
