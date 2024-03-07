package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;

import java.util.*;

public class MeasurementTimeSortHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;

    private MeasurementRepoSubject measurementRepo;

    public MeasurementTimeSortHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler){
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

    }

    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        System.out.println("MeasurementTimeSortHandler");
        HashMap<String, ArrayList<Measurement>> sortedByTime = new HashMap<>();
        for(String type : hashMap.keySet()){
            ArrayList<Measurement> values = hashMap.get(type);
            Collections.sort(values, Comparator.comparing(Measurement::getTimestamp));
            sortedByTime.put(type, values);
//            for (int i = 0; i < values.size(); i++){
//                System.out.println(values.get(i).getMeasurementType() + values.get(i).getTimestamp());
//            }
        }
        nextHandler.handle(sortedByTime);
    }
}
