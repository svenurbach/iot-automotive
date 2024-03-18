package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ComparitiveListErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;
    private double tolerance;
    private int comparativeValuesArraySize;
    private MeasurementRepoSubject measurementRepo;
    private MeasurementService measurementService;

    public ComparitiveListErrorHandler(MeasurementRepoSubject measurementRepo,
                                       MeasurementService measurementService) {
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
    }

    public void setNextHandler(MeasurementHandler nextHandler) {
        // Not implemented
    }


    @Override
    public void handle(Measurement measurement) {
        // Not implemented
    }

    @Override
    public void handle(Trip trip) {
        // Not implemented
    }

    /**
     * Handles the measurement data stored in a HashMap structure, identifying measurement errors and save them in the db.
     *
     * @param hashMap HashMap containing measurement data categorized by measurement types.
     *                Key: String, representing the type of measurement, Value: ArrayList
     *                of Measurement objects.
     */
    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        System.out.println("ComparitiveListErrorHandler");
        comparativeValuesArraySize = 3;
        hashMap.keySet().forEach((type) -> {
            HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
            ArrayList<Double> measurementArrayInDouble = new ArrayList<>();
            // get individual tolerance
            switch (type) {
                case "SpeedMeasurement":
                    tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getSpeedTolerance();
                    break;
                case "AccelerationMeasurement":
                    tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getAccelerationTolerance();
                    break;
                case "LocationMeasurement":
                    tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getLocationTolerance();
                    break;
                case "AxisMeasurement":
                    tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getAxisTolerance();
                    break;
                case "SteeringWheelMeasurement":
                    tolerance = hashMap.get(type).get(0).getVehicle().getVehicleModel().getSteeringWheelTolerance();
                    break;
            }
            measurementArrayInDouble.addAll(measurementService.parseMeasurementArrayToDouble(hashMap.get(type)));
            if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
                System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
                return;
            } else {
            for (int i = 0; i < hashMap.get(type).size(); i++){
                // ignore Measurement with error
                if (hashMap.get(type).get(i).getIsError() == null || !hashMap.get(type).get(i).getIsError()) {
                    ArrayList<Boolean> hasError = new ArrayList<>();
                    if (i >= 0 && i < hashMap.get(type).size() - comparativeValuesArraySize) {
                        boolean isError = measurementService.findErrorInFutureArray(i, hashMap.get(type), measurementArrayInDouble,
                                tolerance, comparativeValuesArraySize);
                        hasError.add(isError);
                    }
                    if (i >= comparativeValuesArraySize) {
                        boolean isError = measurementService.findErrorInPastArray(i, hashMap.get(type), measurementArrayInDouble,
                                tolerance, comparativeValuesArraySize);
                        hasError.add(isError);
                    }
                    boolean hasAtLeastOneError = false;
                    for (boolean isError : hasError) {
                        if (isError) {
                            hasAtLeastOneError = true;
                        }
                    }
                    setErrorOnMeasurement(measurementRepo, hashMap.get(type).get(i), hasAtLeastOneError);
                    processedHashMap.put(type, hashMap.get(type));
                } else {
                    continue;
                }}
            }
            measurementArrayInDouble.clear();
        });
    }
}
