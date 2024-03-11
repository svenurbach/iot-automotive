package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MeasurementRepoSubject {

    private List<MeasurementObserver> observers = new ArrayList<>();

    @Autowired
    private MeasurementRepo measurementRepo;

//    public List<Measurement> findByVehicle_new(Vehicle vehicle) {
//        return measurementRepo.findMeasurements_Vehicle(vehicle);
//    }
//
//    public List<Measurement> findAllMeasurementErrorsPerTrip(boolean error) {
//        return measurementRepo.findMeasurementError(error);
//    }
//    public List<Measurement> findAllMeasurementsFromVehicleWithError(Long id, boolean error){
//        return measurementRepo.findAllMeasurementsFromVehicleWithError(id, error);
//    }


    public List<Measurement> findByVehicle(long vehicleId) {
        return measurementRepo.findByVehicle(vehicleId);
    }

    public Measurement findLastMeasurementByVehicleId(long vehicleId) {
        return measurementRepo.findLastMeasurementByVehicleId(vehicleId);
    }

    public Measurement findLastMeasurementBeforeCurrent(long vehicleId, Measurement measurement) {
        return measurementRepo.findLastMeasurementBeforeCurrent(vehicleId, measurement);
    }

    public Measurement findLastLocationMeasurementByVehicleId(long vehicleId) {
        return measurementRepo.findLastLocationMeasurementByVehicleId(vehicleId);
    }

    public Trip findLastTripByVehicleId(long vehicleId) {
        return measurementRepo.findLastTripByVehicleId(vehicleId);
    }

    public Measurement findLastMeasurementByTripId(long tripId) {
        return measurementRepo.findLastMeasurementByTripId(tripId);
    }


    public List<Measurement> findByMeasurementType(String measurementType) {
        return measurementRepo.findMeasurementType(measurementType);
    }

    public List<Measurement> getAllMeasurementsFromTrip(long id) {
        System.out.println("Ausgabe");
        return measurementRepo.getAllMeasurementsFromTrip(id);
    }

    public List<Measurement> findMeasurementTypeInTrip(String measurementType, long trip) {
        return measurementRepo.findMeasurementTypeInTrip(measurementType, trip);
    }

    public void addObserver(MeasurementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MeasurementObserver observer) {
        observers.remove(observer);
    }

    public void addMeasurement(Measurement measurement) {
        measurementRepo.save(measurement);
        notifyObservers(measurement);
    }



    public void updateMeasurement(Measurement measurement) {
        measurementRepo.save(measurement);
    }

    public Measurement findLastLocationMeasurementByTripId(long tripId) {
        return measurementRepo.findLastLocationMeasurementByTripId(tripId);
    }

    private void notifyObservers(Measurement newMeasurement) {
        for (MeasurementObserver observer : observers) {
            observer.updateMeasurement(newMeasurement);
        }
    }

    public long getTotalMeasurementCount() {
        return measurementRepo.count();
    }

    public void setIsError(Measurement measurement, boolean isError) {
        measurement.setIsError(isError);
        measurementRepo.save(measurement);
    }
}
