package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeasurementService {
//    private final MeasurementRepo repository;

    private final MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }
    // User Story: Messefehler/Inkonsistenzen erkennen
    // https://trello.com/c/1bafUTDM/37-user-story-messfehler-inkonsistenzen-erkennen

//    @Autowired
//    public MeasurementService(MeasurementRepo repository) {
//        this.repository = repository;
//    }

//writeMetric(metric, value, vehicle, trip, )
//    write single metric into db

//    readMeasurements(vehicle)
//    reads all existing metric for a vehicle

//    readAllMeasurementsTrip(vehicle, trip)
//   reads all existing measurements for a vehicle belonging to a trip

//    validateMeasurement(vehicle)
//        validates a metric for errors and returns boolean

//    showError(vehicle)
//        show error in measurement

//    public String getMeasurements(long id) {
//        return measurementRepo.findById(id).get().getMeasuredValue().toString();
//    }

    // fügt Boolean über Messfehler zu Messung hinzu
    public void addMeasurement(Boolean measurementError) {
        Measurement measurement = new Measurement();
        measurement.setIsError(measurementError);
        measurementRepo.save(measurement);
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

    private double roundToTwoDecimalPlaces(Double value) {
        return Math.round(value * 100.0) / 100.0;
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

    // findet Messfehler durch vergleichen der Durchschnittwerte aus dem Vergleichsarray und gibt true oder false zurück,
    // wenn ein Messfehler gefunden wurde (TO-DO: in DB schreiben)
    public boolean findMeasurementError(ArrayList<Double> measurementArrayInDouble, int comparativeValuesArraySize, Double tolerance) {
        if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
            System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
            return true;
        }
        boolean measurementError = false;
        ArrayList<Double> comparativeValuesArrayPast = new ArrayList<>();
        ArrayList<Double> comparativeValuesArrayFuture = new ArrayList<>();
        for (int i = 0; i < measurementArrayInDouble.size(); i++)  {
            if (i < comparativeValuesArraySize) {
                System.out.println("zählt bis zu vergleichenden Wert");
            } else {
                while(comparativeValuesArrayPast.size() < comparativeValuesArraySize) {
                    comparativeValuesArrayPast.add(measurementArrayInDouble.get(i - (comparativeValuesArrayPast.size()+1)));
                }
                double averagePast = calculateAverageMeasurements(comparativeValuesArrayPast);
                if(!(averagePast <= measurementArrayInDouble.get(i) - (measurementArrayInDouble.get(i) * tolerance) ||
                        averagePast <=measurementArrayInDouble.get(i) + (measurementArrayInDouble.get(i) * tolerance))){
                    measurementError = true;
                }
                comparativeValuesArrayPast.clear();
            }
            if (i >= 1){
                while (comparativeValuesArrayFuture.size() <= comparativeValuesArraySize && i < (measurementArrayInDouble.size() - comparativeValuesArraySize +1)){
                    comparativeValuesArrayFuture.add(measurementArrayInDouble.get((i+comparativeValuesArraySize) - (comparativeValuesArrayFuture.size()+1)));
                }
                double averageFuture = calculateAverageMeasurements(comparativeValuesArrayFuture);
                if(!(averageFuture <= measurementArrayInDouble.get(i) - (measurementArrayInDouble.get(i) * tolerance) ||
                        averageFuture <=measurementArrayInDouble.get(i) + (measurementArrayInDouble.get(i) * tolerance))){
                    measurementError = true;
                }
                comparativeValuesArrayFuture.clear();
            }
        System.out.println("Messfehler: " + measurementError);
        }
        addMeasurement(measurementError);
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
