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
    public double convertMeasurementToDouble(Measurement measurement) {
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
     * Checks if a measurement value is within a certain tolerance range of the average.
     *
     * @param average                  Average of the measurements.
     * @param counter                  Index of the measurement value in the ArrayList.
     * @param measurementArrayInDouble ArrayList of double values representing the measurements.
     * @param tolerance                Tolerance value within which the measurement should be.
     * @return True if the measurement value is within the tolerance range of the average, false otherwise.
     */
    public boolean isValueInTolerance(double average, int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance) {
        double valueToCheck = measurementArrayInDouble.get(counter);
        if(valueToCheck == 0){
            valueToCheck = 1;
        }
        double minValue = average - (valueToCheck * tolerance);
        double maxValue = average + (valueToCheck * tolerance);
        double measuredValue = measurementArrayInDouble.get(counter);
        return minValue <= measuredValue && measuredValue <= maxValue;
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
        // Create an ArrayList to store future values
        ArrayList<Double> arrayForFutureValues = new ArrayList<>();
        // Calculate the index to stop considering future values
        int endIndex = values.size() - comparativeValuesArraySize - 1;
        if (counter > endIndex){
            return false;
        }
        // Set the pointer to the next position after the current value
        int pointer = counter + 1;
        // Iterates through the future values to add them to arrayForFutureValues
        while (arrayForFutureValues.size() < comparativeValuesArraySize && pointer < values.size()) {
            // // Adds values with no errors to the arrayForFutureValues
            if (values.get(pointer).getIsError() == null || !values.get(pointer).getIsError()){
                arrayForFutureValues.add(measurementArrayInDouble.get(pointer));
            }
            pointer++;
        }
        // Calculates the average of future values
        double averageFuture = calculateAverage(arrayForFutureValues);
        arrayForFutureValues.clear();
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
        // Checks if the counter is less than comparativeValuesArraySize
        if (counter < comparativeValuesArraySize){
            return false;
        }
        int pointer = counter - 1;
        // Iterates through the past values to add them to arrayForPastValues
        while (arrayForPastValues.size() < comparativeValuesArraySize && pointer >= 0) {
            // Adds values with no errors to the arrayForPastValues
            if (values.get(pointer).getIsError() == null || !values.get(pointer).getIsError()){
                arrayForPastValues.add(measurementArrayInDouble.get(pointer));
            }
            pointer--;
        }
        // Calculates the average of past values
        double averagePast = calculateAverage(arrayForPastValues);
        // Checks if average of past values is within the tolerance range, if not, set measurementError to true
        if (!(isValueInTolerance(averagePast, counter, measurementArrayInDouble, tolerance))) {
            measurementError = true;
        }
        // Clears the arrayForPastValues for future use
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
