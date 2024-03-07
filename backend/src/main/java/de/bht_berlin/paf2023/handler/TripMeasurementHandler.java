package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripMeasurementHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;

    private MeasurementRepoSubject measurementRepo;

    public TripMeasurementHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler){
        this.measurementRepo = measurementRepo;
        setNextHandler(nextHandler);
    }
    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Measurement measurement){

    }

    @Override
    public void handle(Trip trip) {
        System.out.println("TripMeasurementHandler");
        long tripId = trip.getId();
        List<Measurement> measurementsFromTrip = measurementRepo.getAllMeasurementsFromTrip(tripId);
        HashMap<String,  ArrayList<Measurement>> sortedByMeasurementType = new HashMap<>();
        for (int i = 0; i < measurementsFromTrip.size(); i++){
            String measurementType = measurementsFromTrip.get(i).getMeasurementType();
            if(!sortedByMeasurementType.containsKey(measurementType)){
                sortedByMeasurementType.put(measurementType, new ArrayList<>());
            }
            sortedByMeasurementType.get(measurementType).add(measurementsFromTrip.get(i));
        }
        nextHandler.handle(sortedByMeasurementType);
    }

    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> sortMeasurementsFromTrip) {
    }

}
