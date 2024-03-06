package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MeasurementCreationService {

    private int currentLineIndex = 1;
    private boolean schedulerActive;
    List<String> columnHeaders;

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    MeasurementRepoSubject measurementRepo;

    @Autowired
    TripRepo tripRepo;

    public MeasurementCreationService() {
        this.schedulerActive = false;
    }

    @Async
    @Scheduled(fixedDelay = 3000) // Example: Execute every 10 seconds
    @Transactional
    public void processCsvFile() {
        if (schedulerActive == false) {
            return;
        }
        String file = "test.csv";
        if (columnHeaders == null) {
            columnHeaders =
                    MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).getCSVColumnHeaders(file);
        }
        List<HashMap> readout = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFileLineByLine(file,
                columnHeaders,
                currentLineIndex);
        if (readout != null) {
            MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(readout);
        } else {
            Trip unfinishedTrip = tripRepo.findFirstUnfinishedTrip();

            if (unfinishedTrip == null) {
                schedulerActive = false;
            } else {
                Measurement lastMeasurement = measurementRepo.findLastMeasurementByTripId(unfinishedTrip.getId());
                Date currentDate = new Date();
                Date measurementDate = lastMeasurement.getTimestamp();
                if ((currentDate.getTime() - measurementDate.getTime()) > 1000 * 60 * 60) {
                    long vehicleId = lastMeasurement.getVehicle().getId();
                    LocationMeasurement lastLocation = (LocationMeasurement)
                            measurementRepo.findLastLocationMeasurementByVehicleId(vehicleId);
                    unfinishedTrip.finish(lastLocation);
                    unfinishedTrip.setTrip_end(lastLocation.getTimestamp());
                    unfinishedTrip.setEnd_latitude(lastLocation.getLatitude());
                    unfinishedTrip.setEnd_longitude(lastLocation.getLongitude());
                    tripRepo.save(unfinishedTrip);
                }
            }
        }
        currentLineIndex++;
    }

    public void setSchedulerActive(boolean schedulerActive) {
        this.schedulerActive = schedulerActive;
    }
}
