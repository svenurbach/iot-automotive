package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.api.MeasurementController;
import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.service.FakerService;
import de.bht_berlin.paf2023.service.MeasurementService;
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

    @Autowired
    private MeasurementRepo measurementRepo;

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

//        iService.generateDummyDataSet(dataSet);

        System.out.print("Call Singleton");

        List<List<String>> records = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFile("test.csv");
        List<HashMap> allReadOuts = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createHashMap(records);
        System.out.println(allReadOuts);
        MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(allReadOuts);


        ArrayList<Double> testArrayList = new ArrayList<>();
        testArrayList.add(1.0);
        testArrayList.add(2.0);
        testArrayList.add(3.0);
        testArrayList.add(4.0);
        MeasurementService measurementService = new MeasurementService(measurementRepo);
        Boolean error = measurementService.findMeasurementError(testArrayList, 2, 0.2);
        System.out.println(error);
    }
}
