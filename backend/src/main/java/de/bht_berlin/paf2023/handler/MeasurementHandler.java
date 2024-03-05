package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;

import java.util.ArrayList;
import java.util.HashMap;

public interface MeasurementHandler {

    MeasurementRepoSubject measurementRepo = null;
    void handle(Measurement measurement);
    void handle(Trip trip);

    void handle(HashMap<String, ArrayList<Measurement>> sortMeasurementsFromTrip);

    default void setErrorOnMeasurement(Measurement measurement, boolean isError){
        measurementRepo.setIsError(measurement, isError);
    };

}
