package de.bht_berlin.paf2023.config;

import de.bht_berlin.paf2023.component.HandleSingleTripStrategy;
import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.component.SegmentTripsInDBStrategy;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.handler.ComparitiveListErrorHandler;
import de.bht_berlin.paf2023.handler.MeasurementTimeSortHandler;
import de.bht_berlin.paf2023.handler.ThresholdErrorHandler;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import de.bht_berlin.paf2023.service.FakerService;
import de.bht_berlin.paf2023.service.MeasurementCreationService;
import de.bht_berlin.paf2023.service.MeasurementService;
import de.bht_berlin.paf2023.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
public class StartupRunner implements ApplicationRunner {


    @Autowired
    private FakerService iService;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private VehicleModelRepo vehicleModelRepo;
    @Autowired
    private MeasurementRepoSubject measurementRepo;

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private TripRepo tripRepo;

    private TripService service;

    @Autowired
    MeasurementCreationService measurementCreationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /**
         * set up entities to be created by faker
         *
         */
        Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
        dataSet.put("person", 2L);
        dataSet.put("insurance_company", 2L);
        dataSet.put("insurance", 3L);
        dataSet.put("vehicle_model", 3L);
        dataSet.put("vehicle", 3L);
//        dataSet.put("trip", 10L);
        dataSet.put("contract", 3L);

        /**
         * call faker to create set of entities
         */
        iService.generateDummyDataSet(dataSet);

        iService.makePlausibleData();

        /**
         * instantiate handlers for measurement error detection
         */
        ComparitiveListErrorHandler comparitiveListErrorHandler = new ComparitiveListErrorHandler(measurementRepo, measurementService);
        ThresholdErrorHandler thresholdErrorHandler = new ThresholdErrorHandler(measurementRepo, comparitiveListErrorHandler, measurementService, vehicleModelRepo);
        MeasurementTimeSortHandler measurementTimeSortHandler = new MeasurementTimeSortHandler(measurementRepo, thresholdErrorHandler);
        TripMeasurementHandler tripMeasurementHandler = new TripMeasurementHandler(measurementRepo, measurementTimeSortHandler);

        /**
         * instantiate trip service
         */
        TripService service = new TripService(tripRepo, measurementRepo);
        // pass trip measurement handler to trip service
        service.setTripMeasurementHandler(tripMeasurementHandler);

        /**
         * import trips from csv
         */
        // readout csv contents into hashmap
        List<List<String>> records =
                MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).readFile("clean-import.csv");
        List<HashMap> allReadOuts = MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createHashMap(records);

        // call measurement controller to create measurements from hashmap
        MeasurementControllerSingleton.getInstance(vehicleRepo, measurementRepo).createMeasurementEntities(allReadOuts);

        // instantiate strategy go segment trips from import
        SegmentTripsInDBStrategy segmentTripsInDBStrategy = new SegmentTripsInDBStrategy(tripRepo, measurementRepo);

        // set segmenting strategy for csv imports
        service.changeTripHandlerStrategy(segmentTripsInDBStrategy);
        // call segment method on strategy
        service.tripHandlerStrategy.addData(vehicleRepo.findAll());

        /**
         * change trip handling strategy to scheduled readout for new measurements in csv file
         */
        // instantiate and set new strategy
        HandleSingleTripStrategy handleSingleTripStrategy = new HandleSingleTripStrategy(tripRepo, measurementRepo);
        service.changeTripHandlerStrategy(handleSingleTripStrategy);

        // import file and enable scheduler to continuously read csv to simulate incoming measurement stream
//        measurementCreationService.importFile("clean-import.csv");
    }


}
