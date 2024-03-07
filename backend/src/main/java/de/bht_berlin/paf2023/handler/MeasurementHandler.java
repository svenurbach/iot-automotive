package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MeasurementHandler interface defines methods for handling measurements of trips to find measurement errors.
 * Implementing classes should provide implementations for these methods based on specific requirements.
 */
public interface MeasurementHandler {

    MeasurementRepoSubject measurementRepo = null;

    /**
     * Handles an individual measurement.
     *
     * @param measurement The measurement to be handled.
     */
    void handle(Measurement measurement);

    /**
     * Handles measurements from one trip.
     *
     * @param trip The trip to be handled.
     */
    void handle(Trip trip);

    void handle(HashMap<String, ArrayList<Measurement>> sortMeasurementsFromTrip);

    default void setErrorOnMeasurement(Measurement measurement, boolean isError){
        measurementRepo.setIsError(measurement, isError);
    }

}
