package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;

import java.util.ArrayList;
import java.util.HashMap;

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
        comparativeValuesArraySize = 3;
        hashMap.keySet().forEach((type) -> {
            HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
            ArrayList<Double> measurementArrayInDouble = new ArrayList<>();
            // get individual tolerance
            double tolerance = getTolerance(type, hashMap);
            measurementArrayInDouble.addAll(measurementService.parseMeasurementArrayToDouble(hashMap.get(type)));
            if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
                System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
                return;
            } else {
            for (int i = 0; i < hashMap.get(type).size(); i++){
                boolean isError;
                // ignore Measurement with error
                if (hashMap.get(type).get(i).getIsError() == null || !hashMap.get(type).get(i).getIsError()) {
                     isError = checkForErrors(i, measurementArrayInDouble, hashMap, type, tolerance);
                    setErrorOnMeasurement(measurementRepo, hashMap.get(type).get(i), isError);
                }
                processedHashMap.put(type, hashMap.get(type));
                }
            }
            measurementArrayInDouble.clear();
        });
    }


    /**
     * Retrieves the tolerance value based on the type of measurement from the provided HashMap.
     *
     * @param type   Type of measurement for which the tolerance value is needed
     * @param hashMap HashMap containing measurements grouped by type
     * @return       Tolerance value corresponding to the specified measurement type
     */
    private double getTolerance(String type, HashMap<String, ArrayList<Measurement>> hashMap) {
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
        return tolerance;
    }


    /**
     * Checks for the presence of errors for a specific index in a list of measurements (future and past measurements).
     *
     * @param index                    Index of the element to be checked
     * @param measurementArrayInDouble List of measurements converted to double data type
     * @param hashMap                  HashMap containing the measurements grouped by type
     * @param type                     Type of measurements to be checked
     * @param tolerance                Tolerance for error detection
     * @return                         True if errors are present for the specified index, otherwise False
     */
    private boolean checkForErrors(int index, ArrayList<Double> measurementArrayInDouble, HashMap<String, ArrayList<Measurement>> hashMap,
                                   String type, double tolerance) {
        ArrayList<Boolean> hasError = new ArrayList<>();
        if (index >= 0 && index < hashMap.get(type).size() - comparativeValuesArraySize) {
            boolean isError = measurementService.findErrorInFutureArray(index, hashMap.get(type), measurementArrayInDouble,
                    tolerance, comparativeValuesArraySize);
            hasError.add(isError);
        }
        if (index >= comparativeValuesArraySize) {
            boolean isError = measurementService.findErrorInPastArray(index, hashMap.get(type), measurementArrayInDouble,
                    tolerance, comparativeValuesArraySize);
            hasError.add(isError);
        }
        return hasError.stream().anyMatch(Boolean::booleanValue);
    }

    // method for testing
    public boolean checkForErrorsPublic(int index, ArrayList<Double> measurementArrayInDouble, HashMap<String, ArrayList<Measurement>> hashMap,
                                        String type, double tolerance) {
        return checkForErrors(index, measurementArrayInDouble, hashMap, type, tolerance);
    }
}

