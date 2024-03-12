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
     * Sorts measurements in a HashMap by time and passes them to the next handler.
     * Receives a HashMap of measurements grouped by their type and then sort them by time.
     * The sorted measurements are then passed to the next handler.
     *
     * @param hashMap contains measurements grouped by their type.
     *                Key: Type of measurements,Value: ArrayList of measurements of that type.
     */
    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        // HashMap to store sorted measurements by type
        HashMap<String, ArrayList<Measurement>> sortedByTime = new HashMap<>();
        // Iterating over the entries of the given HashMap
        hashMap.forEach((type, values) -> {
            // Sorting measurements by time
            values.sort(Comparator.comparing(Measurement::getTimestamp));
            // Adding sorted measurements to the new HashMap
            sortedByTime.put(type, values);
        });
        // Passing the sorted measurements to the next handler
        nextHandler.handle(sortedByTime);
    }
}
