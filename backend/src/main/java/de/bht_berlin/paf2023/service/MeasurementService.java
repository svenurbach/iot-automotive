package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MeasurementService {
//    private final MeasurementRepo repository;

    private final MeasurementRepoSubject measurementRepo;
//    private final VehicleModelRepo vehicleModelRepo;

    @Autowired
    public MeasurementService(MeasurementRepoSubject measurementRepo) {
        this.measurementRepo = measurementRepo;
//        this.vehicleModelRepo = vehicleModelRepo;
    }


    // fügt Boolean über Messfehler zu Messung hinzu
    public void addMeasurement(Boolean measurementError, Measurement measurement) {
//        Measurement measurement = new Measurement();
        measurement.setIsError(measurementError);
        measurementRepo.addMeasurement(measurement);
    }



    // bekommt Array aus Messungen und parst die enthaltenen Werte zum Typ double
    public ArrayList<Double> parseMeasurementArrayToDouble(ArrayList<Measurement> measurementsArray) {
        ArrayList<Double> measurementArrayList = new ArrayList<>();
        for (Measurement measurement : measurementsArray) {
            measurementArrayList.add(convertMeasurementToDouble(measurement));
        }
        return measurementArrayList;
    }

    private double convertMeasurementToDouble(Measurement measurement) {
        switch (measurement.getMeasurementType()) {
            case "AccelerationMeasurement":
                return roundToTwoDecimalPlaces((double) ((AccelerationMeasurement) measurement).getAcceleration());
            case "AxisMeasurement":
                return roundToTwoDecimalPlaces((double) ((AxisMeasurement) measurement).getAxisAngle());
            case "FuelMeasurement":
                return roundToTwoDecimalPlaces((double) ((FuelMeasurement) measurement).getFuelLevel());
            case "SpeedMeasurement":
                return roundToTwoDecimalPlaces((double) ((SpeedMeasurement) measurement).getSpeed());
            case "SteeringWheelMeasurement":
                return roundToTwoDecimalPlaces((double) ((SteeringWheelMeasurement) measurement).getSteeringWheelAngle());
//            case "TirePressureMeasurement":
//                return roundToTwoDecimalPlaces((double) ((TirePressureMeasurement) measurement).getFrontLeftTire());
//            return roundToTwoDecimalPlaces((double) ((TirePressureMeasurement) measurement).getFrontRightTire());
//            return roundToTwoDecimalPlaces((double) ((TirePressureMeasurement) measurement).getBackLeftTire());
//            return roundToTwoDecimalPlaces((double) ((TirePressureMeasurement) measurement).getBackRightTire());
            default:
                return 0.0;
        }
    }

    public static double roundToTwoDecimalPlaces(Double value) {
        double roundedValue = Math.ceil(Math.abs(value) * 100.0) / 100.0;
        return value < 0 ? -roundedValue : roundedValue;
    }


    // bekommt Array übergeben und berechnet aus diesem den Durchschnittswert
    public double calculateAverageMeasurements(ArrayList<Double> measurementArrayList) {
        double sum = 0;
        for (Double measurement : measurementArrayList) {
            sum += measurement;
        }
        if (!measurementArrayList.isEmpty()) {
            double average = sum / measurementArrayList.size();
            return roundToTwoDecimalPlaces(average);
        } else {
            return 0.0;
        }
    }

    public ArrayList<Measurement> filterMeasurementsPerType(HashMap<String, ArrayList<Measurement>> sortedHashMap) {
        ArrayList<Measurement> singleMeasurementTypeList = new ArrayList<>();
        for (ArrayList<Measurement> values : sortedHashMap.values()) {
            for (Measurement measurement : values) {
                singleMeasurementTypeList.add(measurement);
            }
        }
        return singleMeasurementTypeList;
    }


    public boolean isValueInTolerance(double average, int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance){
        return average - (measurementArrayInDouble.get(counter) * tolerance) <= measurementArrayInDouble.get(counter) &&
                measurementArrayInDouble.get(counter) <= average + (measurementArrayInDouble.get(counter) * tolerance);
    }

    public boolean findErrorInFutureArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble, Double tolerance,
                                          ArrayList<Double> comparativeValuesArrayFuture, int comparativeValuesArraySize){
        boolean measurementError = false;
        while (comparativeValuesArrayFuture.size() < comparativeValuesArraySize && counter <
                (measurementArrayInDouble.size() - comparativeValuesArraySize +1)){
            if(!values.get(counter).getIsError()){
                comparativeValuesArrayFuture.add(measurementArrayInDouble.get((counter+comparativeValuesArraySize) -
                        (comparativeValuesArrayFuture.size())));
            }
        }
        System.out.println("vergleichsarray future: " + comparativeValuesArrayFuture);
        double averageFuture = calculateAverageMeasurements(comparativeValuesArrayFuture);
        System.out.println("durchschnitt future: " + averageFuture);
        if(!(isValueInTolerance(averageFuture, counter, measurementArrayInDouble, tolerance))){
            measurementError = true;
        }
        System.out.println("Messfehler future: " + measurementError);
        comparativeValuesArrayFuture.clear();
        return measurementError;
    }



    public boolean findErrorInPastArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble, Double tolerance,
                                        ArrayList<Double> comparativeValuesArrayPast, int comparativeValuesArraySize){
        boolean measurementError = false;
        while(comparativeValuesArrayPast.size() < comparativeValuesArraySize) {
            if(!values.get(counter).getIsError()){
                comparativeValuesArrayPast.add(measurementArrayInDouble.get(counter - (comparativeValuesArrayPast.size()+1)));
            }
        }
//        System.out.println("vergleichsarray past: " + comparativeValuesArrayPast);
        double averagePast = calculateAverageMeasurements(comparativeValuesArrayPast);
//        System.out.println("durchschnitt past: " + averagePast);
        if(!(isValueInTolerance(averagePast, counter, measurementArrayInDouble, tolerance))){
            measurementError = true;
        }
//        System.out.println("Messfehler past: " + measurementError);
        comparativeValuesArrayPast.clear();
        return measurementError;
    }


    public Map<String, Integer> countErrorsPerMeasurementType(long tripId) {
        Map<String, Integer> errorCounts = new HashMap<>();
        List<Measurement> allMeasurementsFromTrip = measurementRepo.getAllMeasurementsFromTrip(tripId);
        for (int i = 0; i < allMeasurementsFromTrip.size(); i++) {
            if (allMeasurementsFromTrip.get(i).getIsError()) {
                String measurementType = allMeasurementsFromTrip.get(i).getMeasurementType();
                if (!errorCounts.containsKey(measurementType)) {
                    errorCounts.put(measurementType, 1);
                } else {
                    errorCounts.put(measurementType, errorCounts.get(measurementType) + 1);
                }
            }
        }
        return errorCounts;
    }


    public int countErrorsPerWholeTrip(HashMap<String, Integer> errorCountsMeasurementType){
        int errorsTotal = 0;
        for (Map.Entry<String, Integer> entry : errorCountsMeasurementType.entrySet()){
            errorsTotal += entry.getValue();
        }
        return errorsTotal;
        }


}
