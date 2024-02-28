package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public String getMeasurements(long id) {
        return measurementRepo.findById(id).get().getMeasuredValue().toString();
    }


    public ArrayList<Double> parseMeasurementArrayToDouble(Measurement[] measurementsArray) {
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
//                return roundToTwoDecimalPlaces((double) ((TirePressureMeasurement) measurement).getTirePressure());
            default:
                return 0.0;
        }
    }

    private double roundToTwoDecimalPlaces(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }


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

    public boolean findMeasurementError(ArrayList<Double> measurementArrayInDouble, int comparativeValuesArraySize, Double tolerance) {
        if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
            System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
            return false;
        }
        boolean measurementError = true;
        ArrayList<Double> comparativeValuesArrayPast = new ArrayList<>();
        ArrayList<Double> comparativeValuesArrayFuture = new ArrayList<>();
        for (int i = 0; i < measurementArrayInDouble.size(); i++) {
            if (i < comparativeValuesArraySize) {
                break;
            } else {
                while(comparativeValuesArrayPast.size() < comparativeValuesArraySize) {
                    comparativeValuesArrayPast.add(measurementArrayInDouble.get(i));
                }
                double averagePast = calculateAverageMeasurements(comparativeValuesArrayPast);
                if(averagePast <= measurementArrayInDouble.get(i) - (measurementArrayInDouble.get(i) * tolerance) ||
                        averagePast >=measurementArrayInDouble.get(i) + (measurementArrayInDouble.get(i) * tolerance)){
                    measurementError = false;
                }
                while (comparativeValuesArrayFuture.size() < comparativeValuesArraySize){
                    comparativeValuesArrayFuture.add(measurementArrayInDouble.get(i+comparativeValuesArraySize+1));
                }
                double averageFuture = calculateAverageMeasurements(comparativeValuesArrayPast);
                if(averageFuture <= measurementArrayInDouble.get(i) - (measurementArrayInDouble.get(i) * tolerance) ||
                        averageFuture >=measurementArrayInDouble.get(i) + (measurementArrayInDouble.get(i) * tolerance)){
                    measurementError = false;
                }
            }
        }
        return measurementError;
    }
}
