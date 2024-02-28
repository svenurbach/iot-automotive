package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }
    // User Story: Messefehler/Inkonsistenzen erkennen
    // https://trello.com/c/1bafUTDM/37-user-story-messfehler-inkonsistenzen-erkennen

//writeMetric(metric, value, vehicle, trip, )
//    write single metric into db

//    readMeasurements(vehicle)
//    reads all existing metric for a vehicle

//    readAllMeasurementsTrip(vehicle, trip)
//   reads all existing measurements for a vehicle belonging to a trip

//    validateMeasurement(vehicle)
//        validates a metric for errors and returns boolean

//    showError(vehicle)
//        show error in measurement

    public String getMeasurements(long id) {
        return "test";
        // return repository.findById(id).get().get
    }

    public List<Measurement> getMeasurementsByVehicle(long vehicleId) {
        return measurementRepo.findByVehicle(vehicleId);
    }
}
