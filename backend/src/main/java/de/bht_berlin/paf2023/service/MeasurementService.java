package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MeasurementService {

    private final MeasurementRepo measurementRepo;

    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo) {
        this.measurementRepo = measurementRepo;
//        this.vehicleModelRepo = vehicleModelRepo;
    }

    public List<Measurement> findAllMeasurementsFromVehicleWithError(Long id){
        return measurementRepo.findAllMeasurementsFromVehicleWithError(id);
    }


    // fügt Boolean über Messfehler zu Messung hinzu
//    public void addMeasurement(Boolean measurementError, Measurement measurement) {
////        Measurement measurement = new Measurement();
//        measurement.setIsError(measurementError);
//        measurementRepo.addMeasurement(measurement);
//    }


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
            case "FuelMeasurement":
                return roundToTwoDecimalPlaces((double) ((FuelMeasurement) measurement).getFuelLevel());
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
    public double calculateAverageMeasurements(ArrayList<Double> measurementArrayList) {
        double sum = 0;
        for (Double measurement : measurementArrayList) {
            sum += measurement;
        }
        // check if ArrayList is not empty
        if (!measurementArrayList.isEmpty()) {
            double average = sum / measurementArrayList.size();
            return roundToTwoDecimalPlaces(average);
        } else {
            return 0.0;
        }
    }

//    public ArrayList<Measurement> filterMeasurementsPerType(HashMap<String, ArrayList<Measurement>> sortedHashMap) {
//        ArrayList<Measurement> singleMeasurementTypeList = new ArrayList<>();
//        for (ArrayList<Measurement> values : sortedHashMap.values()) {
//            for (Measurement measurement : values) {
//                singleMeasurementTypeList.add(measurement);
//            }
//        }
//        return singleMeasurementTypeList;
//    }


    /**
     * Checks if a measurement value is within a certain tolerance range of the average.
     *
     * @param average Average of the measurements.
     * @param counter Index of the measurement value in the ArrayList.
     * @param measurementArrayInDouble ArrayList of double values representing the measurements.
     * @param tolerance Tolerance value within which the measurement should be.
     * @return True if the measurement value is within the tolerance range of the average, false otherwise.
     */
    public boolean isValueInTolerance(double average, int counter, ArrayList<Double> measurementArrayInDouble, Double tolerance) {
        double lowerBound = average - (measurementArrayInDouble.get(counter) * tolerance);
        double upperBound = average + (measurementArrayInDouble.get(counter) * tolerance);
        double measurementValue = measurementArrayInDouble.get(counter);
        return lowerBound <= measurementValue && measurementValue <= upperBound;
    }

    /**
     * Finds if there is an error in future measurements based on the average of upcoming measurements and tolerance.
     *
     * @param counter Index of the current measurement value
     * @param values ArrayList of Measurement objects
     * @param measurementArrayInDouble ArrayList of double values representing the measurements
     * @param tolerance Tolerance value within which the measurement should be
     * @param comparativeValuesArrayFuture List of double values representing future comparative measurements
     * @param comparativeValuesArraySize Size of the comparative values array
     * @return True if an error is found in future measurements, false otherwise
     */
    public boolean findErrorInFutureArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble,
                                          Double tolerance, List<Double> comparativeValuesArrayFuture, int comparativeValuesArraySize){
        int endIndex = counter + comparativeValuesArraySize;
        ArrayList<Double> futureMeasurements = IntStream.range(counter, Math.min(endIndex, values.size()))
                .filter(i -> values.get(i).getIsError() != null && !values.get(i).getIsError())
                .mapToObj(i -> measurementArrayInDouble.get(i))
                .collect(Collectors.toCollection(ArrayList::new));
        double averageFuture = calculateAverageMeasurements(futureMeasurements);

        return !isValueInTolerance(averageFuture, counter, measurementArrayInDouble, tolerance);
    }

    /**
     * Finds if there is an error in past measurements based on the average of past comparative measurements and tolerance.
     *
     * @param counter Index of the current measurement value
     * @param values ArrayList of Measurement objects
     * @param measurementArrayInDouble ArrayList of double values representing the measurements
     * @param tolerance Tolerance value within which the measurement should be
     * @param comparativeValuesArrayPast ArrayList of double values representing past comparative measurements
     * @param comparativeValuesArraySize Size of the comparative values array
     * @return True if an error is found in past measurements, false otherwise
     */
    public boolean findErrorInPastArray(int counter, ArrayList<Measurement> values, ArrayList<Double> measurementArrayInDouble, Double tolerance,
                                        ArrayList<Double> comparativeValuesArrayPast, int comparativeValuesArraySize){
        boolean measurementError = false;
        while(comparativeValuesArrayPast.size() < comparativeValuesArraySize) {
            if(values.get(counter).getIsError() == null || !values.get(counter).getIsError()){
                comparativeValuesArrayPast.add(measurementArrayInDouble.get(counter - (comparativeValuesArrayPast.size()+1)));
            }
        }
        double averagePast = comparativeValuesArrayPast.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        if(!(isValueInTolerance(averagePast, counter, measurementArrayInDouble, tolerance))){
            measurementError = true;
        }
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
