package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

public class ComparitiveListErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;
    private double tolerance;
    private int comparativeValuesArraySize;

    private MeasurementRepoSubject measurementRepo;
    private MeasurementService measurementService;

    public ComparitiveListErrorHandler(MeasurementRepoSubject measurementRepo,
//                                       MeasurementHandler nextHandler,
                                       MeasurementService measurementService){
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
//        setNextHandler(nextHandler);
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
        System.out.println("ComparitiveListErrorHandler");
        HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
        for (String type : hashMap.keySet()){
            ArrayList<Measurement> values = hashMap.get(type);
            ArrayList<Double> measurementArrayInDouble = measurementService.parseMeasurementArrayToDouble(values);
            if (measurementArrayInDouble.size() < comparativeValuesArraySize) {
                System.out.println("Nicht genügend Werte im Array, um Messfehler zu identifizieren");
//                // set isError on Measurement in DB to NULL
                continue;
            }
            ArrayList<Double> comparativeValuesArrayPast = new ArrayList<>();
            ArrayList<Double> comparativeValuesArrayFuture = new ArrayList<>();
            for (int i = 0; i < measurementArrayInDouble.size(); i++)  {
                if (values.get(i).getIsError() != null) {
                    // ignore Measurement with error
                    if(values.get(i).getIsError()){
                        continue;
                    }
                    boolean isError = false;
                    System.out.println("i:" + measurementArrayInDouble.get(i));
                    if (i < comparativeValuesArraySize) {
                        System.out.println("zählt bis zu vergleichenden Wert");
                    } else {
                        isError = measurementService.findErrorInPastArray(i, values, measurementArrayInDouble, tolerance,
                                comparativeValuesArrayPast, comparativeValuesArraySize);
                    }
                    if (i >= 0 && i < measurementArrayInDouble.size() - comparativeValuesArraySize){
                        isError = measurementService.findErrorInFutureArray(i, values, measurementArrayInDouble, tolerance,
                                comparativeValuesArrayFuture, comparativeValuesArraySize);
                    }
                    setErrorOnMeasurement(values.get(i), isError);
                }
            }
            processedHashMap.put(type, values); // Füge den verarbeiteten HashMap-Eintrag hinzu
        }
//        nextHandler.handle(processedHashMap);
    }

}
