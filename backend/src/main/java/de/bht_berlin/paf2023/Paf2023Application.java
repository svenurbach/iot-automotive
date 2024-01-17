package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.api.MeasurementController;
import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.service.FakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.bht_berlin.paf2023.repo.VehicleRepo;

import java.sql.Array;
import java.util.*;

@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

    @Autowired
    private FakerService iService;

    @Autowired
    private VehicleRepo vehicleRepo;

    public static void main(String[] args) {

        SpringApplication.run(Paf2023Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
        dataSet.put("person", 10L);
        dataSet.put("insurance", 10L);
        dataSet.put("vehicle_model", 10L);
        dataSet.put("vehicle", 10L);
        dataSet.put("trip", 10L);
        dataSet.put("contract", 10L);

        iService.generateDummyDataSet(dataSet);

        System.out.print("Call Singleton");

        List<List<String>> records = MeasurementControllerSingleton.getInstance(vehicleRepo).readFile("test.csv");
        List<HashMap> allReadOuts = MeasurementControllerSingleton.getInstance(vehicleRepo).createHashMap(records);
        System.out.println(allReadOuts);
        MeasurementControllerSingleton.getInstance(vehicleRepo).createMeasurementEntities(allReadOuts);

    }
}
