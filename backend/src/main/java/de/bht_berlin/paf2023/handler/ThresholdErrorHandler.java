package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.AccelerationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.service.MeasurementService;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


public class ThresholdErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;

    private MeasurementRepoSubject measurementRepo;
    private VehicleModelRepo vehicleModelRepo;
    private MeasurementService measurementService;

    public ThresholdErrorHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler,
                                 MeasurementService measurementService, VehicleModelRepo vehicleModelRepo) {
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
        this.vehicleModelRepo = vehicleModelRepo;
        setNextHandler(nextHandler);
    }

    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Measurement measurement) {

    }

    @Override
    public void handle(Trip trip) {

    }

    @Override
//    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
//        System.out.println("ThresholdErrorHandler");
//        HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
//        for (String type : hashMap.keySet()) {
//            ArrayList<Measurement> values = hashMap.get(type);
//            for (Measurement measurement : values) {
//                    if (measurement.getIsError() != null) {
//                        if (measurement.getIsError()) {
//                            continue;
//                        }
////                    }
//                        boolean isError = false;
//                        if (measurement.getMeasurementType().equals("SpeedMeasurement")) {
//                            Float maxSpeed = measurement.getVehicle().getVehicleModel().getMaxSpeed();
//                            SpeedMeasurement tempSpeed = (SpeedMeasurement) measurement;
//                            Float currentSpeed = (float) tempSpeed.getSpeed();
//                            if (maxSpeed > currentSpeed) {
//                                isError = true;
//                            }
//                        }
//                        if (measurement.getMeasurementType().equals("AccelerationMeasurement")) {
//                            Float maxAcceleration = measurement.getVehicle().getVehicleModel().getMaxAcceleration();
//                            AccelerationMeasurement tempAcc = (AccelerationMeasurement) measurement;
//                            Float currentAcceleration = (float) tempAcc.getAcceleration();
//                            if (maxAcceleration > currentAcceleration) {
//                                isError = true;
//                            }
//                        }
//                        setErrorOnMeasurement(measurement, isError);
//                    }
//            }
//            processedHashMap.put(type, values);
//        }
//        nextHandler.handle(processedHashMap);
//    }

    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        System.out.println("ThresholdErrorHandler");

        // Verarbeitung der Messungen mit Streams
        HashMap<String, ArrayList<Measurement>> processedHashMap = hashMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> processMeasurements(entry.getValue()),
                        (oldValue, newValue) -> oldValue,
                        HashMap::new));

        nextHandler.handle(processedHashMap);
    }

    private ArrayList<Measurement> processMeasurements(ArrayList<Measurement> measurements) {
        return measurements.stream()
                .map(this::processMeasurement)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Measurement processMeasurement(Measurement measurement) {
        if (measurement.getIsError() != null && !measurement.getIsError()) {
            if ("SpeedMeasurement".equals(measurement.getMeasurementType())) {
                Float maxSpeed = measurement.getVehicle().getVehicleModel().getMaxSpeed();
                SpeedMeasurement speedMeasurement = (SpeedMeasurement) measurement;
                Float currentSpeed = (float) speedMeasurement.getSpeed();
                measurement.setIsError(maxSpeed <= currentSpeed);
            } else if ("AccelerationMeasurement".equals(measurement.getMeasurementType())) {
                Float maxAcceleration = measurement.getVehicle().getVehicleModel().getMaxAcceleration();
                AccelerationMeasurement accelerationMeasurement = (AccelerationMeasurement) measurement;
                Float currentAcceleration = (float) accelerationMeasurement.getAcceleration();
                measurement.setIsError(maxAcceleration <= currentAcceleration);
            }
        }
        return measurement;
    }
}
