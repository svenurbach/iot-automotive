package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.api.MeasurementController;
import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.FakerService;
import de.bht_berlin.paf2023.service.MeasurementService;
import de.bht_berlin.paf2023.service.TripService;
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

    @Autowired
    private TripRepo tripRepo;

    public static void main(String[] args) {

        SpringApplication.run(Paf2023Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
//        dataSet.put("person", 10L);
//        dataSet.put("insurance_company", 10L);
//        dataSet.put("insurance", 10L);
        dataSet.put("vehicle_model", 10L);
        dataSet.put("vehicle", 2L);
//        dataSet.put("trip", 10L);
//        dataSet.put("contract", 1L);

        iService.generateDummyDataSet(dataSet);

        System.out.print("Call Singleton");

        List<List<String>> records = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFile("test.csv");
        List<HashMap> allReadOuts = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createHashMap(records);
        MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(allReadOuts);

        List<Measurement> list = measurementRepo.findByVehicle(1);
        System.out.println(list.size());

        List<Measurement> list2 = measurementRepo.findByMeasurementType("SpeedMeasurement");
        System.out.println(list2.size());

        TripService service = new TripService(tripRepo, measurementRepo);
        Vehicle existingVehicle = this.vehicleRepo.getById(1L);
        service.segmentDataIntoTrips(existingVehicle);

        ArrayList<Double> testArrayList = new ArrayList<>();
        testArrayList.add(1.0);
        testArrayList.add(2.02);
        testArrayList.add(3.0);
        testArrayList.add(4.0);
        testArrayList.add(5.0);
        testArrayList.add(6.0);
        testArrayList.add(7.0);
        testArrayList.add(8.0);
        MeasurementService measurementService = new MeasurementService(measurementRepo);
        boolean error = measurementService.findMeasurementError(testArrayList, 3, 0.9);
//        System.out.println("Messfehler:" + error);
//       System.out.println(tripRepo.findById(11L));
//       System.out.println(measurementService.calculateAverageMeasurements(testArrayList));

    }
}
