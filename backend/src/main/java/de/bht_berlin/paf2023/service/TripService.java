package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class TripService {
    private final TripRepo repository;

    @Autowired
    public TripService(TripRepo repository) {
        this.repository = repository;
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
            if (measurementList.get(i).getMeasurementType() == "LocationMeasurement") {
                startLocation = (LocationMeasurement) measurementList.get(i);
            }
        }
        for (int i = measurementList.size() - 1; i > 1; i--) {
            if (measurementList.get(i).getMeasurementType() == "LocationMeasurement") {
                endLocation = (LocationMeasurement) measurementList.get(i);
            }
        }
        Trip trip = startTrip(startLocation, measurementList.get(0).getVehicle());
        updateTrip(trip, (Measurement) measurementList);
        endTrip(trip, endLocation);
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
