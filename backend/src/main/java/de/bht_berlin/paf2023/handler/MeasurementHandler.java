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

    /**
     * Handles all measurements from one trip.
     *
     * @param measurementsFromTrip The trip to be handled.
     */
    void handle(HashMap<String, ArrayList<Measurement>> measurementsFromTrip);


    /**
     * Sets the error status on a measurement object and updates it in the measurement repository.
     *
     * @param measurementRepo Repository subject where the measurement object is stored
     * @param measurement Measurement object for which the error status needs to be set
     * @param isError Boolean value indicating whether the measurement is marked as an error (true) or not (false)
     */
    default void setErrorOnMeasurement(MeasurementRepoSubject measurementRepo, Measurement measurement, boolean isError){
        measurementRepo.setIsError(measurement, isError);
    }

}
