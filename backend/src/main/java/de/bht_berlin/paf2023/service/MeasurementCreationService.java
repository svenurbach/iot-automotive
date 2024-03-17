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
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MeasurementCreationService {

    /**
     * counter to remember already read lines
     */
    private int currentLineIndex = 1;

    private final int timeBetweenTripsInMinutes = 60;

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

    /**
     * run schedule method to read out csv line by line on given interval
     */
    @Async
    @Scheduled(fixedDelay = 4000) // interval in millis
    @Transactional
    public void processCsvFile() {

        // early return if scheduler is set to false
        if (schedulerActive == false) {
            return;
        }

        // path to csv which is to be read line by line
        String file = "test3.csv";

        // retrieve column headers from csv file and store in list of strings
        if (columnHeaders == null) {
            columnHeaders =
                    MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).getCSVColumnHeaders(file);
        }

        // create hashmap of read line
        List<HashMap> readout = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFileLineByLine(file,
                columnHeaders,
                currentLineIndex);

        if (readout != null) {
            // create measurement entities from hashmap
            MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(readout);
        } else {
            // if hashmap contains no more data, ergo all file contents is read, find an unfinished trip
            Trip unfinishedTrip = tripRepo.findFirstUnfinishedTrip();

            if (unfinishedTrip == null) {
                // set scheduler inactive if all trips are finished
                schedulerActive = false;
            } else {
                // retrieve last measurement of found unfinished trip
                Measurement lastMeasurement = measurementRepo.findLastMeasurementByTripId(unfinishedTrip.getId());

                // get current date
                Date currentDate = new Date();

                // compare current date with timestamp of last measurement
                Date measurementDate = lastMeasurement.getTimestamp();

                // end trip if time difference is greater than set interval
                if ((currentDate.getTime() - measurementDate.getTime()) > 1000 * 60 * this.timeBetweenTripsInMinutes) {

                    // get vehicle id from trip
                    long vehicleId = lastMeasurement.getVehicle().getId();

                    // find last location measurement
                    LocationMeasurement lastLocation = (LocationMeasurement)
                            measurementRepo.findLastLocationMeasurementByVehicleId(vehicleId);

                    // set trip to finish and save to repo
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
