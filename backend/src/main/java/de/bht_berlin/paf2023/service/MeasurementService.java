package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeasurementService {

    private final MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
    }

    /**
     * Parses an ArrayList of Measurement objects into an ArrayList of double values.
     *
     * @param measurementsArray ArrayList of Measurement objects to parse
     * @return ArrayList of double values representing the measurements
     */
    public ArrayList<Double> parseMeasurementArrayToDouble(ArrayList<Measurement> measurementsArray) {
        ArrayList<Double> measurementArrayList = new ArrayList<>();
        for (Measurement measurement : measurementsArray) {
            measurementArrayList.add(convertMeasurementToDouble(measurement));
        }
        return measurementArrayList;
    }

    /**
     * Converts a Measurement object to a double value based on its type.
     *
     * @param measurement Measurement object to convert
     * @return converted double value
     */
    private double convertMeasurementToDouble(Measurement measurement) {
        switch (measurement.getMeasurementType()) {
            case "AccelerationMeasurement":
                return roundToTwoDecimalPlaces((double) ((AccelerationMeasurement) measurement).getAcceleration());
            case "AxisMeasurement":
                return roundToTwoDecimalPlaces((double) ((AxisMeasurement) measurement).getAxisAngle());
            case "SpeedMeasurement":
                return roundToTwoDecimalPlaces((double) ((SpeedMeasurement) measurement).getSpeed());
            case "SteeringWheelMeasurement":
                return roundToTwoDecimalPlaces((double) ((SteeringWheelMeasurement) measurement).getSteeringWheelAngle());
            default:
                return 0.0;
        }
    }

    public static double roundToTwoDecimalPlaces(Double value) {
        double roundedValue = Math.ceil(Math.abs(value) * 100.0) / 100.0;
        return value < 0 ? -roundedValue : roundedValue;
    }


    /**
     * Calculates the average of the measurements in an ArrayList of double values.
     *
     * @param measurementArrayList ArrayList of double values representing the measurements
     * @return Average of measurements, rounded to two decimal places
     */
//    public double calculateAverageMeasurements(ArrayList<Double> measurementArrayList) {
//        double sum = 0;
//        for (Double measurement : measurementArrayList) {
//            sum += measurement;
//        }
//        // check if ArrayList is not empty
//        if (!measurementArrayList.isEmpty()) {
//            double average = sum / measurementArrayList.size();
//            return roundToTwoDecimalPlaces(average);
//        } else {
//            return 0.0;
//        }
//    }

    /**
     * Checks if a measurement value is within a certain tolerance range of the average.
     *
     * @param average                  Average of the measurements.
     * @param counter                  Index of the measurement value in the ArrayList.
     * @param measurementArrayInDouble ArrayList of double values representing the measurements.
     * @param tolerance                Tolerance value within which the measurement should be.
     * @return True if the measurement value is within the tolerance range of the average, false otherwise.
     */
    public boolean isValueInTolerance(double average, int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance) {
        double minValue = average - (measurementArrayInDouble.get(counter) * tolerance);
        double maxValue = average + (measurementArrayInDouble.get(counter) * tolerance);
        double measurementValue = measurementArrayInDouble.get(counter);
        return minValue <= measurementValue && measurementValue <= maxValue;
    }

    /**
     * Finds if there is an error in future measurements based on the average of upcoming measurements and tolerance.
     *
     * @param counter                      Index of the current measurement value
     * @param values                       ArrayList of Measurement objects
     * @param measurementArrayInDouble     ArrayList of double values representing the measurements
     * @param tolerance                    Tolerance value within which the measurement should be
     * @param comparativeValuesArraySize   Size of the comparative values array
     * @return True if an error is found in future measurements, false otherwise
     */
    public boolean findErrorInFutureArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble,
                                          Double tolerance, int comparativeValuesArraySize) {
        ArrayList<Double> arrayForFutureValues = new ArrayList<>();
        int endIndex = values.size() - comparativeValuesArraySize - 1;
        if (counter > endIndex){
            return false;
        }
        int pointer = counter + 1;
        while (arrayForFutureValues.size() < comparativeValuesArraySize && pointer < values.size()) {
            if (values.get(pointer).getIsError() == null || !values.get(pointer).getIsError()){
                arrayForFutureValues.add(measurementArrayInDouble.get(pointer));
            }
            pointer++;
        }
        double averageFuture = calculateAverage(arrayForFutureValues);
//        arrayForFutureValues.clear();
        return !isValueInTolerance(averageFuture, counter, measurementArrayInDouble, tolerance);
    }

    /**
     * Finds if there is an error in past measurements based on the average of past comparative measurements and tolerance.
     *
     * @param counter                    Index of the current measurement value
     * @param values                     ArrayList of Measurement objects
     * @param measurementArrayInDouble   ArrayList of double values representing the measurements
     * @param tolerance                  Tolerance value within which the measurement should be
     * @param comparativeValuesArraySize Size of the comparative values array
     * @return True if an error is found in past measurements, false otherwise
     */
    public boolean findErrorInPastArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble,
                                        Double tolerance, int comparativeValuesArraySize) {
        boolean measurementError = false;
        ArrayList<Double> arrayForPastValues = new ArrayList<>();
        if (counter < comparativeValuesArraySize){
            return false;
        }
        int pointer = counter - 1;
        while (arrayForPastValues.size() < comparativeValuesArraySize && pointer >= 0) {
            if (values.get(pointer).getIsError() == null || !values.get(pointer).getIsError()){
                arrayForPastValues.add(measurementArrayInDouble.get(pointer));
            }
            pointer--;
        }
        double averagePast = calculateAverage(arrayForPastValues);
        if (!(isValueInTolerance(averagePast, counter, measurementArrayInDouble, tolerance))) {
            measurementError = true;
        }
        arrayForPastValues.clear();
        return measurementError;
    }

    public double calculateAverage(ArrayList<Double> arrayValues){
        double average =  arrayValues.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        return average;
    }

}
