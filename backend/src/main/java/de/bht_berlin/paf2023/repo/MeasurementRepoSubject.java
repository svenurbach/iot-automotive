package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
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

    public List<Measurement> findByVehicle(long vehicleId) {
        return measurementRepo.findByVehicle(vehicleId);
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



    private void notifyObservers(Measurement newMeasurement) {
        for (MeasurementObserver observer : observers) {
            observer.updateMeasurement(newMeasurement);
        }
    }

    public void setIsError(Measurement measurement, boolean isError) {
        System.out.println("set Error");
        measurement.setIsError(isError);
        measurementRepo.save(measurement);
    }
}
