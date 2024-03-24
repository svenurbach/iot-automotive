package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handler class responsible for processing measurements related to a trip.
 */
public class TripMeasurementHandler implements MeasurementHandler {

    private MeasurementHandler nextHandler;
    private MeasurementRepoSubject measurementRepo;

    /**
     * Constructs a TripMeasurementHandler with the specified MeasurementRepoSubject and next handler.
     *
     * @param measurementRepo MeasurementRepoSubject to use for retrieving measurements.
     * @param nextHandler     Next handler in the chain to pass the processed measurements to.
     */
    public TripMeasurementHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler){
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

    /**
     * Handles a Trip object by retrieving measurements associated with it from the repository,
     * grouping them by type, and passing them to the next handler.
     *
     * @param trip Trip object to be handled.
     */
    @Override
    public void handle(Trip trip) {
        // Retrieve ID of the trip
        long tripId = trip.getId();
        // Retrieve all measurements associated with trip from repo
        List<Measurement> measurementsFromTrip = measurementRepo.getAllMeasurementsFromTrip(tripId);
        // HashMap to store measurements grouped by their type
        HashMap<String,  ArrayList<Measurement>> sortedByMeasurementType = new HashMap<>();
        for (int i = 0; i < measurementsFromTrip.size(); i++){
            // Get type of the current measurement
            String measurementType = measurementsFromTrip.get(i).getMeasurementType();
            // If HashMap does not contain the measurement type, create a new ArrayList for it
            if(!sortedByMeasurementType.containsKey(measurementType)){
                sortedByMeasurementType.put(measurementType, new ArrayList<>());
            }
            // Add the current measurement to the ArrayList corresponding to its type
            sortedByMeasurementType.get(measurementType).add(measurementsFromTrip.get(i));
        }
        // Passing the sorted measurements to the next handler
        nextHandler.handle(sortedByMeasurementType);
    }

    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> sortMeasurementsFromTrip) {
        // Not implemented
    }
}
