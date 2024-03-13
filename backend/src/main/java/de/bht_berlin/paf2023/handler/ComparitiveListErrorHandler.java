package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class ComparitiveListErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;
    private double tolerance;
    private int comparativeValuesArraySize;
    private MeasurementRepoSubject measurementRepo;
    private MeasurementService measurementService;

    public ComparitiveListErrorHandler(MeasurementRepoSubject measurementRepo,
//                                       MeasurementHandler nextHandler,
                                       MeasurementService measurementService) {
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
    }

    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }


    @Override
    public void handle(Measurement measurement) {
        // Not implemented
    }

    @Override
    public void handle(Trip trip) {
        // Not implemented
    }

    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        System.out.println("ComparitiveListErrorHandler");
        comparativeValuesArraySize = 3;
        HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
        System.out.println("Hashmap: " + hashMap);
        ArrayList<Double> measurementArrayInDouble = new ArrayList<>();
        hashMap.keySet().forEach((type) -> {
            measurementArrayInDouble.addAll(measurementService.parseMeasurementArrayToDouble(hashMap.get(type)));
            System.out.println("hashMap.get(type): " + hashMap.get(type));
        });
        hashMap.keySet().forEach((type) -> {
            // get individual tolerance
            switch (type){
                case "SpeedMeasurement": tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getSpeedTolerance();
                    break;
                case "AccelerationMeasurement": tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getAccelerationTolerance();
                    break;
                case "LocationMeasurement": tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getLocationTolerance();
                    break;
//                case "AxisMeasurement": tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getAxisTolerance();
//                break;
//                case "SteeringWheelMeasurement": tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getSteeringWheelTolerance();
//                break;
            }

            if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
                System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren" + " " + type + " " + measurementArrayInDouble.size());
                return;
            }
            ArrayList<Double> comparativeValuesArrayPast = new ArrayList<>();
            ArrayList<Double> comparativeValuesArrayFuture = new ArrayList<>();

            IntStream.range(0, hashMap.get(type).size()).forEach(i -> {
                // ignore Measurement with error
                if (hashMap.get(type).get(i).getIsError() != null && hashMap.get(type).get(i).getIsError()) {
//                    continue;
                } else {
                    ArrayList<Boolean> hasError = new ArrayList<>();
                if (i >= comparativeValuesArraySize) {
                    boolean isError =  measurementService.findErrorInPastArray(i, hashMap.get(type), measurementArrayInDouble, tolerance,
                            comparativeValuesArrayPast, comparativeValuesArraySize);
                    hasError.add(isError);
                    System.out.println("isError past " + isError);
                }
                    if (i >= 0 && i < hashMap.get(type).size() - comparativeValuesArraySize) {
                        boolean isError = measurementService.findErrorInFutureArray(i, hashMap.get(type), measurementArrayInDouble, tolerance,
                                comparativeValuesArrayFuture, comparativeValuesArraySize);
                        hasError.add(isError);
                        System.out.println(comparativeValuesArrayFuture);
                        System.out.println("isError future " + isError);

                    }
                    boolean hasAtLeastOneError = false;
                    for (boolean isError: hasError) {
                        if (isError == true){
                            hasAtLeastOneError = true;
//                            System.out.println("isError = true " + );
                        }
                    }                    setErrorOnMeasurement(measurementRepo, hashMap.get(type).get(i), hasAtLeastOneError);
                    processedHashMap.put(type, hashMap.get(type));
                }
            });
            System.out.println("ComparitiveListErrorHandler Ende");
        });
    }
}
