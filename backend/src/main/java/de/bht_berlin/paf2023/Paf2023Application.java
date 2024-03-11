package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.component.SegmentTripsInDBStrategy;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.handler.ComparitiveListErrorHandler;
import de.bht_berlin.paf2023.handler.MeasurementTimeSortHandler;
import de.bht_berlin.paf2023.handler.ThresholdErrorHandler;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.repo.*;
import de.bht_berlin.paf2023.service.FakerService;
import de.bht_berlin.paf2023.service.MeasurementService;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;

//@EnableScheduling
@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

    @Autowired
    private FakerService iService;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private VehicleModelRepo vehicleModelRepo;

    @Autowired
    private MeasurementRepo measurementRepo;

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private VehicleService vehicleService;

    private TripMeasurementHandler tripMeasurementHandler;
    private MeasurementTimeSortHandler measurementTimeSortHandler;
    private ThresholdErrorHandler thresholdErrorHandler;
    private ComparitiveListErrorHandler comparitiveListErrorHandler;

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

//        boolean error = measurementService.findMeasurementError(testArrayList, 3, 0.9);
//        System.out.println("Messfehler:" + error);
//       System.out.println(measurementService.calculateAverageMeasurements(testArrayList));

//        System.out.println(measurementService.getAllMeasurementsFromTrip(1L));
//        HashMap<String, ArrayList<Measurement>> hashMap = measurementService.getAllMeasurementsFromTrip(1L);
//        System.out.println(measurementService.sortMeasurementsFromTrip(hashMap));
//
//        System.out.println(measurementService.findErrorPerTrip(hashMap, 2, 0.9));

// Tests Handler to find Measurement Errors
//        comparitiveListErrorHandler = new ComparitiveListErrorHandler(measurementRepo, measurementService);
//        thresholdErrorHandler = new ThresholdErrorHandler(measurementRepo, comparitiveListErrorHandler, measurementService, vehicleModelRepo);
//        measurementTimeSortHandler = new MeasurementTimeSortHandler(measurementRepo, thresholdErrorHandler);
//        tripMeasurementHandler = new TripMeasurementHandler(measurementRepo, measurementTimeSortHandler);
//        Trip trip = tripRepo.getById(1L);
//        tripMeasurementHandler.handle(trip);
//
//        System.out.println("vehicleService.getVehicleModel(1L)");

//        System.out.println(vehicleService.getAllVehicleModels());

    }
}
