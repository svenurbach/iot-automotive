package de.bht_berlin.paf2023.repo;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
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

    public void setMeasurementRepo(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }

    public void addObserver(MeasurementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MeasurementObserver observer) {
        observers.remove(observer);
    }

    /**
     * saves measurement to repo and notifies injectjed observers
     *
     * @param measurement measurement object to be saved to db
     */
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

    public void setIsError(Measurement measurement, boolean isError) {
        System.out.println("setIsError");
        measurement.setIsError(isError);
        measurementRepo.save(measurement);
    }

    /***
     *  forwarding calls to Measurement Repo and passing back return values to calling method
     * */

    public long getTotalMeasurementCount() {
        return measurementRepo.count();
    }

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

    public Measurement findLastLocationBeforeNewMeasurement(long vehicleId,
                                                            Measurement newMeasurement) {
        return measurementRepo.findLastLocationBeforeNewMeasurement(vehicleId, newMeasurement);
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
        return measurementRepo.getAllMeasurementsFromTrip(id);
    }

    public List<Measurement> findMeasurementTypeInTrip(String measurementType, long trip) {
        return measurementRepo.findMeasurementTypeInTrip(measurementType, trip);
    }
}
