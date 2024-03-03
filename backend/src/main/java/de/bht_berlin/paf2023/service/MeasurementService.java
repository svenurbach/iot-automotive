package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


//    public String getMeasurements(long id) {
//        return measurementRepo.findById(id).get().getMeasuredValue().toString();
//    }

    // fügt Boolean über Messfehler zu Messung hinzu
    public void addMeasurement(Boolean measurementError, Measurement measurement) {
//        Measurement measurement = new Measurement();
        measurement.setIsError(measurementError);
        measurementRepo.addMeasurement(measurement);
    }



    // bekommt Array aus Messungen und parst die enthaltenen Werte zum Typ double
    public ArrayList<Double> parseMeasurementArrayToDouble(List<Measurement> measurementsArray) {
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

    // (wenn Fahrt FINISHED ist) bekommt alle Messwerte, ordnet diese zu Messtyp
    // und speichert sie in Map {Messtyp: [Messung1, Messung2]; ...}
    public HashMap<String,  ArrayList<Measurement>> getAllMeasurementsFromTrip(long tripId){
            List<Measurement> measurementsFromTrip = measurementRepo.getAllMeasurementsFromTrip(tripId);
            HashMap<String,  ArrayList<Measurement>> sortedMeasurements = new HashMap<>();
            for (int i = 0; i < measurementsFromTrip.size(); i++){
                String measurementType = measurementsFromTrip.get(i).getMeasurementType();
                if(!sortedMeasurements.containsKey(measurementType)){
                    sortedMeasurements.put(measurementType, new ArrayList<>());
                }
                sortedMeasurements.get(measurementType).add(measurementsFromTrip.get(i));
        }
            return sortedMeasurements;
    }

    public HashMap<String, ArrayList<Measurement>> sortMeasurementsFromTrip(HashMap<String, ArrayList<Measurement>> hashMap){
        HashMap<String, ArrayList<Measurement>> sorted = new HashMap<>();
        for(String type : hashMap.keySet()){
            ArrayList<Measurement> values = hashMap.get(type);
            Collections.sort(values, Comparator.comparing(Measurement::getTimestamp));
            sorted.put(type, values);
            for (int i = 0; i < values.size(); i++){
                System.out.println(values.get(i).getMeasurementType() + values.get(i).getTimestamp());
            }
        }
        return sorted;
    }

    public boolean findErrorPerTrip(HashMap<String, ArrayList<Measurement>> sortedMap, int comparativeValuesArraySize, Double tolerance){
        boolean error = false;
        for(String type : sortedMap.keySet()){
            ArrayList<Measurement> measurementsOfType = sortedMap.get(type);
            ArrayList<Double> arrayInDouble = parseMeasurementArrayToDouble(measurementsOfType);
            if(findErrorInList(arrayInDouble, comparativeValuesArraySize, tolerance)){
                System.out.println("Fehler gefunden");
                error = true;
            }
        }
        return error;
    }

    // findet Messfehler durch vergleichen der Durchschnittwerte aus dem Vergleichsarray und gibt true oder false zurück,
    // wenn ein Messfehler gefunden wurde. Schreibt error (true/false) in DB
    public boolean findErrorInList(ArrayList<Double> measurementArrayInDouble, int comparativeValuesArraySize, Double tolerance) {
        if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
            System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
            return true;
        }
        boolean measurementError = false;
        ArrayList<Double> comparativeValuesArrayPast = new ArrayList<>();
        ArrayList<Double> comparativeValuesArrayFuture = new ArrayList<>();
        for (int i = 0; i < measurementArrayInDouble.size(); i++)  {
            System.out.println("i:" + measurementArrayInDouble.get(i));
            if (i < comparativeValuesArraySize) {
                System.out.println("zählt bis zu vergleichenden Wert");
            } else {
                measurementError= findErrorInPastArray(i, measurementArrayInDouble, tolerance,
                        comparativeValuesArrayPast, comparativeValuesArraySize);
            }
            if (i >= 0 && i < measurementArrayInDouble.size() - comparativeValuesArraySize){
                measurementError = findErrorInFutureArray(i, measurementArrayInDouble, tolerance,
                        comparativeValuesArrayFuture, comparativeValuesArraySize);
            }
        System.out.println("Messfehler: " + measurementError);
//            addMeasurement(measurementError, measurementArrayInDouble.get(i));
        }
        return measurementError;
    }

    public boolean isValueInTolerance(double average, int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance){
        return average - (measurementArrayInDouble.get(counter) * tolerance) <= measurementArrayInDouble.get(counter) &&
                measurementArrayInDouble.get(counter) <= average + (measurementArrayInDouble.get(counter) * tolerance);
    }

    public boolean findErrorInFutureArray(int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance,
                                          ArrayList<Double> comparativeValuesArrayFuture, int comparativeValuesArraySize){
        boolean measurementError = false;
        while (comparativeValuesArrayFuture.size() < comparativeValuesArraySize && counter < (measurementArrayInDouble.size() - comparativeValuesArraySize +1)){
            comparativeValuesArrayFuture.add(measurementArrayInDouble.get((counter+comparativeValuesArraySize) - (comparativeValuesArrayFuture.size())));
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

    public boolean findErrorInPastArray(int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance,
                                        ArrayList<Double> comparativeValuesArrayPast, int comparativeValuesArraySize){
        boolean measurementError = false;
        while(comparativeValuesArrayPast.size() < comparativeValuesArraySize) {
            comparativeValuesArrayPast.add(measurementArrayInDouble.get(counter - (comparativeValuesArrayPast.size()+1)));
        }
        System.out.println("vergleichsarray past: " + comparativeValuesArrayPast);
        double averagePast = calculateAverageMeasurements(comparativeValuesArrayPast);
        System.out.println("durchschnitt past: " + averagePast);
        if(!(isValueInTolerance(averagePast, counter, measurementArrayInDouble, tolerance))){
            measurementError = true;
        }
        System.out.println("Messfehler past: " + measurementError);
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

    public int countErrorsPerWholeTrip(Map<String, Integer> errorCountsMeasurementType){
        int errorsTotal = 0;
        for (Map.Entry<String, Integer> entry : errorCountsMeasurementType.entrySet()){
            errorsTotal += entry.getValue();
        }
        return errorsTotal;
        }
}
