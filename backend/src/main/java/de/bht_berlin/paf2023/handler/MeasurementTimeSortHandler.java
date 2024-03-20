package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Handler class responsible for sorting measurements by time.
 */
public class MeasurementTimeSortHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;
    private MeasurementRepoSubject measurementRepo;

    /**
     * Constructs a MeasurementTimeSortHandler with the specified MeasurementRepoSubject and next handler.
     *
     * @param measurementRepo MeasurementRepoSubject to use for retrieving measurements.
     * @param nextHandler     Next handler in the chain to pass the sorted measurements to.
     */
    public MeasurementTimeSortHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler){
        this.measurementRepo = measurementRepo;
        setNextHandler(nextHandler);
    }

    /**
     * Sets the next handler in the chain.
     *
     * @param nextHandler Next handler to be set.
     */
    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Measurement measurement){
        // Not implemented
    }

    @Override
    public void handle(Trip trip) {
        // Not implemented
    }

    /**
     * Handles a HashMap of measurements.
     * This method processes the given HashMap of measurements by sorting them by time for each measurement type
     * and passes the sorted measurements to the next handler.
     *
     * @param hashMap contains measurements grouped by their type.
     *                Key: Type of measurements,Value: ArrayList of measurements of that type.
     */
    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        // HashMap to store sorted measurements by type
        HashMap<String, ArrayList<Measurement>> sortedByTime = processHashMap(hashMap);
        // Passing the sorted measurements to the next handler
        nextHandler.handle(sortedByTime);
    }


    /**
     * Processes a HashMap of measurements.
     * This method sorts the measurements within each ArrayList in the given HashMap by their timestamps.
     *
     * @param hashMap HashMap containing lists of measurements, where the key is the type of measurement.
     * @return New HashMap with measurements sorted by time for each measurement type
     */
    private HashMap<String, ArrayList<Measurement>> processHashMap(HashMap<String, ArrayList<Measurement>> hashMap){
        HashMap<String, ArrayList<Measurement>> sortedByTime = new HashMap<>();
        // Iterating over the entries of the given HashMap
        hashMap.forEach((type, values) -> {
            // Sorting measurements by time
            values.sort(Comparator.comparing(Measurement::getTimestamp));
            // Adding sorted measurements to the new HashMap
            sortedByTime.put(type, values);
        });
        return sortedByTime;
    }

    public HashMap<String, ArrayList<Measurement>> processHashMapPublic(HashMap<String, ArrayList<Measurement>> hashMap) {
        return processHashMap(hashMap);
    }
}
