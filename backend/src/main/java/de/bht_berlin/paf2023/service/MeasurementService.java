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
    private final MeasurementRepo repository;

    // User Story: Messefehler/Inkonsistenzen erkennen
    // https://trello.com/c/1bafUTDM/37-user-story-messfehler-inkonsistenzen-erkennen

    @Autowired
    public MeasurementService(MeasurementRepo repository) {
        this.repository = repository;
    }

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
        return repository.findById(id).get().getMeasuredValue().toString();
    }

    // returns Array with n past measurements
    public Measurement[] getAmountOfPastMeasurements(int n, String measurementType) {
        List<Measurement> measurements = repository.findTopNByOrderByTimestampDesc(n, measurementType);
        Measurement[] measurementsArray = new Measurement[n];
        for (int i = 0; i < n; i++) {
            measurementsArray[i] = measurements.get(i);
        }
        return measurementsArray;
    }


    public ArrayList<Double> parseMeasurementArrayToDouble(Measurement[] measurementsArray) {
        ArrayList<Double> measurementArrayList = new ArrayList<>();
        for (Measurement measurement : measurementsArray) {
            measurementArrayList.add(convertMeasurementToDouble(measurement));
        }
        return measurementArrayList;
    }

    private Double convertMeasurementToDouble(Measurement measurement) {
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
                return null;
        }
    }

    private Double roundToTwoDecimalPlaces(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }


    public Double calculateAverageMeasurements(ArrayList<Double> measurementArrayList) {
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

    public Boolean findMeasurementError(ArrayList<Double> measurementArrayInDouble, int comparativeValuesArraySize, Double toleranz) {
        Boolean measurementError = true;
        ArrayList<Double> comparativeValuesArray = new ArrayList<>();
        for (int i = 0; i < measurementArrayInDouble.size(); i++) {
            if (i < comparativeValuesArraySize) {
                break;
            } else {
                while(comparativeValuesArray.size() < comparativeValuesArraySize) {
                    comparativeValuesArray.add(measurementArrayInDouble.get(i));
                }
                Double average = calculateAverageMeasurements(comparativeValuesArray);
                if(average <= measurementArrayInDouble.get(i) - (measurementArrayInDouble.get(i) * toleranz) || average >=measurementArrayInDouble.get(i) + (measurementArrayInDouble.get(i) * toleranz)){
                    measurementError = false;
                }
            }
        }
        return measurementError;
    }


}
