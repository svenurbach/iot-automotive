package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public MeasurementCreationService() {
        this.schedulerActive = false;
    }

    @Async
    @Scheduled(fixedDelay = 3000) // Example: Execute every 10 seconds
    @Transactional
    public void processCsvFile() {
        if (schedulerActive == false) {
            System.out.println("scheduled off");
            return;
        }
        System.out.println("scheduled");
//        System.out.println("Count: " + measurementRepo.getTotalMeasurementCount());
//        System.out.println("currentLineIndex: " + currentLineIndex);
        String file = "test.csv";
        if (columnHeaders == null) {
            columnHeaders =
                    MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).getCSVColumnHeaders(file);
        }
//        System.out.println("Count: " + measurementRepo.getTotalMeasurementCount());
//        System.out.println("currentLineIndex: " + currentLineIndex);

        List<HashMap> readout = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFileLineByLine(file,
                columnHeaders,
                currentLineIndex);
        if (readout != null) {
//            System.out.println(readout);
//            System.out.println("singleton instance id2");
//            System.out.println(MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo));
            MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(readout);
        }
        currentLineIndex++;
    }

    public void setSchedulerActive(boolean schedulerActive) {
        this.schedulerActive = schedulerActive;
    }
}
